package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Manages the login interface and operations
 */
public class LoginScreen extends AppCompatActivity {

    Button loginButton, registerButton;
    EditText usernameField, passwordField;
    String username, password;  // extracted from EditText objects
    SharedPreferences prefs;
    SharedPreferences.Editor prefEditor;
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

    /**
     * Helper method that attempts to log-in a user with the given username and password
     * Upon a successful login, their information will be stored in SharedPrefs to keep them logged in, a websocket will be opened, and they will be taken to the main screen of the app.
     * Upon an unsuccessful login, the user will be notified whether their username or password was invalid
     * @param username user-supplied username
     * @param password user-supplies password
     */
    private void login(final String username, final String password) {

        // create a GET request for user profile
        JsonObjectRequest loginrequest = new JsonObjectRequest(Request.Method.GET, Const.URL_GET_PROFILE_BY_USERNAME + usernameField.getText(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int usertype = 0; // class of user (basic, mod, admin), default to basic

                        // if username of response is "&&&", username is invalid
                        try{
                            if (response.getString(Const.PROFILE_USERNAME_KEY).equals("&&&")){
                                Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_LONG).show();
                                return;
                            }
                        } catch (JSONException jsone) {
                            Log.d(Const.LOGTAG_JSONOBJECT_READ, "Could not extract username from JSON");
                            Toast.makeText(getApplicationContext(), "An error occurred, please try again", Toast.LENGTH_LONG).show();
                            return;
                        }

                        try {
                            // if given password is incorrect, let user know and don't continue
                            if (!password.equals(response.getString(Const.PROFILE_PASSWORD_KEY))) {
                                Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                                return;
                            }

                            // get usertype from response
                            usertype = response.getInt(Const.PROFILE_MOD_FLAG_KEY);
                        } catch (JSONException jse) {
                            Toast.makeText(getApplicationContext(), jse.toString(), Toast.LENGTH_LONG).show();
                        }

                        // if password is correct...
                        // 1: store profile information in SharedPrefs
                        prefEditor.putString(Const.SHAREDPREFS_USERNAME_KEY, username); // store username in SharedPrefs
                        prefEditor.putInt(Const.SHAREDPREFS_USERTYPE_KEY, usertype); // store usertype in SharedPrefs
                        //TODO store more profile information as needed
                        prefEditor.apply(); // apply changes to SharedPrefs

                        // 2: start websocket as user
                        appState = (GlobalState) getApplicationContext();
                        Log.d(Const.LOGTAG_WEBSOCKET, "Starting websocket with username: " + username); // log websocket start
                        appState.startWebsocket(username); // start websocket

                        // 3: open main screen
                        changeScreen = new Intent(LoginScreen.this, MainAppScreen.class);
                        startActivity(changeScreen);
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