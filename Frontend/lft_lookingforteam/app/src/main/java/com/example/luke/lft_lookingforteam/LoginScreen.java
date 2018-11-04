package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.net.URI;
import java.net.URISyntaxException;

public class LoginScreen extends AppCompatActivity {

    Button loginButton, registerButton;
    EditText usernameField, passwordField;
    String username, password;  // extracted from EditText objects
    SharedPreferences prefs;
    SharedPreferences.Editor prefEditor;
    URI webSocketURI;
    public WebSocketClient chatSocket;
    Intent changeScreen;
    RequestQueue reqQueue;

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

        //temporary testing stuff
        final Button sendButton = findViewById(R.id.websocketTest_sendButton);
        final EditText msgBox = findViewById(R.id.websocketTest_msgBox);
        final TextView conversation = findViewById(R.id.websocketTest_conversation);
        //end of testing stuff

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

                            conversation.append("login successful" + "\n");

                            // 2: establish websocket with server and request cached messages
                            try {
                                webSocketURI = new URI(Const.URL_OPEN_WEBSOCKET + username);
                            } catch (URISyntaxException uriSE) {
                                conversation.append(uriSE.toString() + "/n");
                            }

                            conversation.append(webSocketURI.toString() + "\n");

                            chatSocket = new WebSocketClient(webSocketURI) {
                                @Override
                                public void onOpen(ServerHandshake serverHandshake) {
                                    conversation.append("connection established" + "\n");
                                    // request cached messages
                                    chatSocket.send("g:");
                                }

                                @Override
                                public void onMessage(String s) {
                                    //TODO update/create relevant conversation file

                                    //temporary testing function
                                    conversation.append(s + "\n");
                                }

                                @Override
                                public void onClose(int i, String s, boolean b) {

                                }

                                @Override
                                public void onError(Exception e) {
                                    //TODO log error
                                    conversation.append(e.toString() + "\n");
                                }
                            };

                            //TODO delete testing stuff and uncomment step 3 after testing websockets
                            // testing stuff
                            sendButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String message = msgBox.getText().toString();
                                    conversation.append(message + "\n");
                                    chatSocket.send("m:socketTest@" + message);
                                }
                            });

                            // end of testing stuff

//                            // 3: open swipe screen based on usertype
//                            if (usertype == Const.USERTYPE_BASIC_USER) {
//                                changeScreen = new Intent(LoginScreen.this, UserSwipeScreen.class);
//                            } else if (usertype == Const.USERTYPE_MODERATOR) {
//                                changeScreen = new Intent(LoginScreen.this, ModSwipeScreen.class);
//                            } else if (usertype == Const.USERTYPE_ADMIN) {
//                                changeScreen = new Intent(LoginScreen.this, AdminSwipeScreen.class);
//                            } else {
//                                // if something goes wrong and usertype doesn't match any defined types, default to basic user
//                                changeScreen = new Intent(LoginScreen.this, UserSwipeScreen.class);
//                            }
//                            startActivity(changeScreen);
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