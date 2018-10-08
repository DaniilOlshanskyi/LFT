package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.luke.lft_lookingforteam.net_utils.Const;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountRegistrationScreen extends AppCompatActivity {

    Button submitButton;
    EditText usernameField, passwordField;
    String username, password;
    StringBuilder unpwdErrorMsg = new StringBuilder();     // used to tell user what's wrong with their username/password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_registration_screen);

        // referencing layout objects
        submitButton = findViewById(R.id.acctRegSubmitButton);
        usernameField = findViewById(R.id.accountCreation_username);
        passwordField = findViewById(R.id.accountCreation_password);

        // enforce character limit on username field
        InputFilter[] unFilterArray = new InputFilter[1];
        unFilterArray[0] = new InputFilter.LengthFilter(Const.USERNAME_CHARACTER_LIMIT);
        usernameField.setFilters(unFilterArray);

        // enforce character limit on password field
        InputFilter[] pwdFilterArray = new InputFilter[1];
        pwdFilterArray[0] = new InputFilter.LengthFilter(Const.PASSWORD_CHARACTER_LIMIT);
        passwordField.setFilters(pwdFilterArray);

        // when the "submit" button is pressed
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get strings from text typed into "username" and "password" fields
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();

                // check to make sure username isn't already in use
                if (!usernameValidation(username, unpwdErrorMsg)) {
                    // if username is invalid/unavailable, tell user
                    Toast.makeText(getApplicationContext(), unpwdErrorMsg, Toast.LENGTH_LONG).show();
                }
                // if it's available, continue
                else {
                    // check whether password is valid
                    if (!passwordValidation(password, unpwdErrorMsg)) {
                        // if password is invalid, tell user
                        Toast.makeText(getApplicationContext(), unpwdErrorMsg, Toast.LENGTH_LONG).show();
                    }
                    // if it's valid, continue
                    else {

                        // create POST request to send to server
                        StringRequest postReq = new StringRequest(Request.Method.POST, Const.URL_POST_PROFILE,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // notify user that their account has been created :]
                                        Toast.makeText(getApplicationContext(), "Account created :]", Toast.LENGTH_LONG).show();

                                        // switch to Account Viewing Screen, passing username
                                        Intent i = new Intent(AccountRegistrationScreen.this, AccountViewScreen.class);
                                        i.putExtra("PROFILE_USERNAME", username);
                                        startActivity(i);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Prof_POST_Req", error.toString());
                                    }
                                }) {
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put(Const.PROFILE_USERNAME_KEY, username);
                                params.put(Const.PROFILE_PASSWORD_KEY, password);
                                params.put(Const.PROFILE_PERIOD_KEY, "availability not set");
                                params.put(Const.PROFILE_PHOTO_KEY, "");
                                params.put(Const.PROFILE_REPORT_FLAG_KEY, "0");
                                params.put(Const.PROFILE_MOD_FLAG_KEY, "0");
                                params.put(Const.PROFILE_REPUTATION_KEY, "0.0");
                                params.put(Const.PROFILE_LATEST_LOGIN_DATE_KEY, new java.sql.Date(System.currentTimeMillis()).toString());
                                params.put(Const.PROFILE_SUSPENDED_KEY, "0.0");
                                return params;
                            }
                        };

                        // make POST request
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(postReq);
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

        // clear errorMsg to prepare for appension
        errorMsg.delete(0, errorMsg.length());

        // check password length, notify user and return false if less than 8 characters
        if (pwd.length() < 8) {
            errorMsg.append("Password is too short");
            return false;
        }

        // check whether password contains a letter
        // this pattern defines a regular expression where there is: 0 or more characters,
        // then at least one letter (capital or lowercase), then 0 or more characters
        final String letterPattern = ".*(?=[a-zA-Z]+).*";
        pattern = Pattern.compile(letterPattern);
        matcher = pattern.matcher(pwd);
        // if the matcher created from this pattern doesn't find a match in the password,
        // then we know it doesn't contain any letters, so let the user know and return false
        if (!matcher.matches()) {
            errorMsg.append("Password must contain at least one letter");
            return false;
        }

        // check whether password contains a number
        // this pattern defines a regular expression where there is: 0 or more characters,
        // then at least one number, then 0 or more characters
        final String numPattern = ".*(?=[0-9]+).*";
        pattern = Pattern.compile(numPattern);
        matcher = pattern.matcher(pwd);
        // if the matcher created from this pattern doesn't find a match in the password,
        // then we know it doesn't contain any numbers, so let the user know and return false
        if (!matcher.matches()) {
            errorMsg.append("Password must contain at least one number");
            return false;
        }

        // check whether password contains a special character
        // this pattern defines a regular expression where there is: 0 or more characters,
        // then at least one special character, then 0 or more characters
        final String specCharPattern = ".*(?=[@$!%*#?&]+).*";
        pattern = Pattern.compile(specCharPattern);
        matcher = pattern.matcher(pwd);
        // if the matcher created from this pattern doesn't find a match in the password,
        // then we know it doesn't contain any special characters, so let the user know and return false
        if (!matcher.matches()) {
            errorMsg.append("Password must contain at least one special character");
            return false;
        }

        // check whether password contains any restricted characters
        // this pattern defines a regular expression where there is: 0 or more characters,
        // then at least one restricted character, then 0 or more characters
        final String restrCharPattern = ".*(?=[=<>+,.]+).*";
        pattern = Pattern.compile(restrCharPattern);
        matcher = pattern.matcher(pwd);
        // if the matcher created from this pattern finds a match in the password,
        // then we know it contains a restricted character, so notify the user and return false
        if (matcher.matches()){
            errorMsg.append("Password cannot contain restricted characters (=<>+,.)");
            return false;
        }

        // if no issues are found with the password, return true
        return true;
    }

    // username availability method
    private boolean usernameValidation(String username, StringBuilder errorMsg) {

        // pattern and matcher objects used to check for validity
        Pattern pattern;
        Matcher matcher;

        // clear errorMsg to prepare for appension
        errorMsg.delete(0, errorMsg.length());

        // check username length, notify user and return false if somehow longer than the limit
        if (username.length() > Const.USERNAME_CHARACTER_LIMIT){
            errorMsg.append("Usernames cannot be longer than " + Const.USERNAME_CHARACTER_LIMIT + " characters");
            return false;
        }

        // make sure username doesn't contain any restricted characters (&=<>+,)
        // this pattern defines a regular expression where there is: 0 or more characters,
        // then at least one of the restricted characters, then 0 or more characters
        final String restrCharPattern = ".*(?=[&=<>+,.]+).*";
        pattern = Pattern.compile(restrCharPattern);
        matcher = pattern.matcher(username);
        // if the matcher created from this pattern finds a match in the username,
        // then we know it contains a restricted character, so notify the user and return false
        if (matcher.matches()) {
            errorMsg.append("Username cannot contain restricted characters (&=<>+,.)");
            return false;
        }

        // TODO make database query request to spring with username


        // if username is available, return true
        return true;
    }
}