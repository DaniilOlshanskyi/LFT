package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.luke.lft_lookingforteam.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

//TODO finish layout design and implement login code

public class LoginScreen extends AppCompatActivity {

    Button loginButton, registerButton;
    EditText usernameField, passwordField;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        loginButton = findViewById(R.id.loginLoginButton);
        registerButton = findViewById(R.id.loginRegisterButton);
        usernameField = findViewById(R.id.loginScreen_username);
        passwordField = findViewById(R.id.loginScreen_password);

        // when the "login" button is pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO show "in progress" loading icon
                //TODO attempt to log user in

                // upon successful login:
                //if(successful login){
                    //TODO store username so it can be accessed across all activities
                    //TODO open websocket with server

                    // switch to swipe screen based on class of user
                    //TODO implement user/mod/admin checking

                    // if basic user, switch to user swipe screen
                    Intent i = new Intent(LoginScreen.this, UserSwipeScreen.class);
                    startActivity(i);
                //}
                // if login credentials are incorrect, let user know
                //else{
                //do something
                //}

            }
        });

        // when the "register" button is pressed
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to account registration screen
                Intent i = new Intent(LoginScreen.this, AccountRegistrationScreen.class);
                startActivity(i);
            }
        });
    }

    //TODO implement login function
    private void login(String username, String password){

        // create a GET request for user profile
        JsonObjectRequest testrequest = new JsonObjectRequest(Request.Method.GET, Const.URL_GET_PROFILE_BY_USERNAME + usernameField.getText(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // when server sends back profile object
                        try{
                            if(response.getString(Const.PROFILE_PASSWORD_KEY).equals(passwordField.getText())){

                            }
                        } catch (JSONException jse){
                            // if an error occurs with the JSON, log it
                            Log.d("Login", jse.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if a volley error occurs, log it
                        Log.d("Prof_GET_Req", error.toString());
                    }
                });

        // make GET request
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(testrequest);
    }
}
