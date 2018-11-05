package com.example.luke.lft_lookingforteam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.luke.lft_lookingforteam.net_utils.Const;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static com.example.luke.lft_lookingforteam.R.layout.conversation_list_screen;

public class LoginScreen extends AppCompatActivity {

    Button loginButton, registerButton;
    EditText usernameField, passwordField;
    String username, password;  // extracted from EditText objects
    SharedPreferences prefs;
    SharedPreferences.Editor prefEditor;
    URI webSocketURI;
    WebSocketClient chatSocket;
    Intent changeScreen;
    RequestQueue reqQueue;
    GlobalState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        loginButton = findViewById(R.id.loginScreen_loginButton);
        registerButton = findViewById(R.id.loginScreen_registerButton);
        usernameField = findViewById(R.id.loginScreen_username);
        passwordField = findViewById(R.id.loginScreen_password);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefEditor = prefs.edit();

        reqQueue = Volley.newRequestQueue(getApplicationContext());

        // when the "login" button is pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO show "in progress" loading icon

                // extract username and password from fields
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();

                // attempt to log user in with given username and password
                login(username, password);
            }
        });

        // when the "register" button is pressed
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to account registration screen
                changeScreen = new Intent(LoginScreen.this, AccountRegistrationScreen.class);
                startActivity(changeScreen);
            }
        });
    }

    // attempts to log user in, letting them know if their credentials are incorrect
    private void login(final String username, final String password) {

        // create a GET request for user profile
        JsonObjectRequest loginrequest = new JsonObjectRequest(Request.Method.GET, Const.URL_GET_PROFILE_BY_USERNAME + usernameField.getText(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // if server response is empty, username is invalid, so let user know and don't continue
                        if (response.length() == 0) {
                            Toast.makeText(getApplicationContext(), "Invalid Username", Toast.LENGTH_LONG).show();
                            return;
                        }

                        try {
                            // if given password is incorrect, let user know and don't continue
                            if (!password.equals(response.getString(Const.PROFILE_PASSWORD_KEY))) {
                                Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                                return;
                            }
                            // if password is correct...
                            // 1: store profile information in SharedPrefs
                            prefEditor.putString(Const.SHAREDPREFS_USERNAME_KEY, username); // store username in SharedPrefs
                            int usertype = response.getInt(Const.PROFILE_MOD_FLAG_KEY); // get usertype from response
                            prefEditor.putInt(Const.SHAREDPREFS_USERTYPE_KEY, usertype); // store usertype in SharedPrefs
                            //TODO store more profile information as needed
                            prefEditor.apply(); // apply changes to SharedPrefs

                            // 2: establish websocket with server, set global messaging websocket, and request cached messages
                            try {
                                webSocketURI = new URI(Const.URL_OPEN_WEBSOCKET + username);
                            } catch (URISyntaxException uriSE) {
                            }
                            chatSocket = new WebSocketClient(webSocketURI) {
                                @Override
                                public void onOpen(ServerHandshake serverHandshake) {
                                    // request cached messages
                                    chatSocket.send("g:");
                                }

                                @Override
                                public void onMessage(String s) {
                                    String senderUsername;
                                    String message;

                                    // 1: parse message for contents
                                    String[] messageElements = s.split("@");
                                    senderUsername = messageElements[0];
                                    message = messageElements[1];

                                    // 2: update conversation file and conversation card
                                    File fileDir = getDir("conversation_files", MODE_PRIVATE);   // get file directory for conversation files
                                    File[] fileList = fileDir.listFiles();  // get list of conversation files

                                    // iterate over existing conversation files
                                    for (File f : fileList) {
                                        // if conversation file already exists for sender,
                                        if (f.getName().equals(senderUsername)) {
                                            // add received message to it...
                                            try {
                                                FileWriter fw = new FileWriter(f, true);
                                                fw.append(s + "\n");
                                                fw.close();
                                            } catch (IOException ioe) {
                                                //TODO log error
                                            }

                                            // and update conversation card in ConversationListScreen:

                                            //TODO make sure this questionable code will work
                                            // get constraint parent view of conversation buttons
                                            LayoutInflater inf = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            View convoList = inf.inflate(R.layout.conversation_list_screen, (ViewGroup) findViewById(R.id.conversationListScreen_conversationList));

                                            // get id of convo card for sender
                                            Resources res = convoList.getResources();
                                            int id = res.getIdentifier(senderUsername, "id", getApplicationContext().getPackageName());

                                            // get convo card button
                                            Button convoCard = (Button) convoList.findViewById(id);

                                            // update card text with message
                                            if (message.length() < 20) {
                                                convoCard.setText(senderUsername + "\n" + message);
                                            } else {
                                                convoCard.setText(senderUsername + "\n" + message.substring(0, 16) + "...");
                                            }

                                            //TODO turn on new message icon for this card
                                        }
                                    }
                                }

                                @Override
                                public void onClose(int i, String s, boolean b) {
                                    //TODO figure out if we need to do anything with this
                                }

                                @Override
                                public void onError(Exception e) {
                                    //TODO log error
                                }
                            };
                            chatSocket.connect();
                            // set global websocket for chat
                            appState = (GlobalState) getApplicationContext();
                            appState.setChatClient(chatSocket);

                            // 3: open swipe screen based on usertype
                            if (usertype == Const.USERTYPE_BASIC_USER) {
                                changeScreen = new Intent(LoginScreen.this, UserSwipeScreen.class);
                            } else if (usertype == Const.USERTYPE_MODERATOR) {
                                changeScreen = new Intent(LoginScreen.this, ModSwipeScreen.class);
                            } else if (usertype == Const.USERTYPE_ADMIN) {
                                changeScreen = new Intent(LoginScreen.this, AdminSwipeScreen.class);
                            } else {
                                // if something goes wrong and usertype doesn't match any defined types, default to basic user
                                changeScreen = new Intent(LoginScreen.this, UserSwipeScreen.class);
                            }
                            startActivity(changeScreen);

                        } catch (JSONException jse) {
                            Toast.makeText(getApplicationContext(), jse.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        // make GET request
        reqQueue.add(loginrequest);
    }
}