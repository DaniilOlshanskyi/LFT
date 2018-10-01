package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.luke.lft_lookingforteam.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountRegistrationScreen extends AppCompatActivity {

    Button submitButton;
    EditText usernameField, passwordField;
    String username, password;
    StringBuilder pwdErrorMsg = new StringBuilder();     // used to tell user what's wrong with their password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_registration_screen);

        // referencing layout objects
        submitButton = findViewById(R.id.acctRegSubmitButton);
        usernameField = findViewById(R.id.accountCreation_username);
        passwordField = findViewById(R.id.accountCreation_password);

        // when the "submit" button is pressed
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get strings from text typed into "username" and "password" fields
                // both text fields are limited to 45 characters in the layout,
                // which is the max size of our database variables for username and password
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();

                // check to make sure username isn't already in use
                if (!usernameAvailable(username)) {
                    // if username is unavailable, tell user
                    Toast.makeText(getApplicationContext(), "Username unavailable", Toast.LENGTH_LONG).show();
                }
                // if it's available, continue
                else {
                    // check whether password is valid
                    if (!passwordValidation(password, pwdErrorMsg)) {
                        // if password is invalid, tell user why
                        Toast.makeText(getApplicationContext(), pwdErrorMsg, Toast.LENGTH_LONG).show();
                    }
                    // if it's valid, continue
                    else {

                        // TODO send account creation request to server (done?)
                        // not really sure what's going on here right now
                        StringRequest postReq = new StringRequest(Request.Method.POST, Const.URL_POST_REQUEST, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("POSTreq", response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("POSTreq", "Error: " + error.getMessage());
                            }
                        }) {
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put(Const.PROFILE_USERNAME_KEY, username);
                                params.put(Const.PROFILE_PASSWORD_KEY, password);
                                return params;
                            }
                        };

                        // notify user that their account has been created :]
                        Toast.makeText(getApplicationContext(), "Account created :]", Toast.LENGTH_LONG).show();

                        // TODO pass id of user account to accountview
                        Intent i = new Intent(AccountRegistrationScreen.this, AccountViewScreen.class);
                        startActivity(i);
                    }
                }
            }
        });
    }

    // password validation method
    private boolean passwordValidation(String pwd, StringBuilder errorMsg) {

        // pattern and matcher objects used to check for validity
        Pattern pattern;
        Matcher matcher;

        // clear errorMsg to prepare for appending to it
        errorMsg.delete(0, errorMsg.length());

        // check password length
        if (pwd.length() < 8) {
            errorMsg.append("Password is too short");
            return false;
        }

        // TODO check syntax of these checks

        // check whether password contains a letter
        final String letterPattern = ".*(?=[a-zA-Z]+).*";
        pattern = Pattern.compile(letterPattern);
        matcher = pattern.matcher(pwd);
        if (!matcher.matches()) {
            errorMsg.append("Password must contain at least one letter");
            return false;
        }

        // check whether password contains a number
        final String numPattern = ".*(?=[0-9]+).*";
        pattern = Pattern.compile(numPattern);
        matcher = pattern.matcher(pwd);
        if (!matcher.matches()) {
            errorMsg.append("Password must contain at least one number");
            return false;
        }

        // check whether password contains a special character
        final String specCharPattern = ".*(?=[@$!%*#?&]+).*";
        pattern = Pattern.compile(specCharPattern);
        matcher = pattern.matcher(pwd);
        if (!matcher.matches()) {
            errorMsg.append("Password must contain at least one special character");
            return false;
        }

        // if no issues are found with the password, return true
        return true;

        // not really sure how this works
//        Pattern pattern;
//        Matcher matcher;
//        final String PWD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@$!%*#?&])(?=\\S+$).{4,}$";
//        pattern = Pattern.compile(PWD_PATTERN);
//        matcher = pattern.matcher(pwd);
//
//        return matcher.matches();
    }

    // username availability method
    private boolean usernameAvailable(String username) {

        // TODO make request to spring with username


        // if username is available, return true
        return true;
    }
}
