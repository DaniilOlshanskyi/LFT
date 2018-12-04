package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages the registration screen interface and operations
 */
public class AccountRegistrationScreen extends AppCompatActivity {

    private RequestQueue reqQueue;

    Button submitButton;
    EditText usernameField, passwordField;
    String username, password;
    StringBuilder unpwdErrorMsg = new StringBuilder();     // used to tell user what's wrong with their username/password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_registration_screen);

        //TODO figure out how to prevent users from pressing "back" to get back to this screen from the MyProfileViewScreen

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

                        // create JSON object to POST
                        JSONObject newAcct = new JSONObject();
                        try {
                            JSONObject put = newAcct.put(Const.PROFILE_USERNAME_KEY, username);
                            newAcct.put(Const.PROFILE_PASSWORD_KEY, password);
                            newAcct.put(Const.PROFILE_PERIOD_KEY, "availability not set");
                            newAcct.put(Const.PROFILE_PHOTO_KEY, Const.URL_DEFAULT_PROFILE_PIC);
                            newAcct.put(Const.PROFILE_REPORT_FLAG_KEY, 0);
                            newAcct.put(Const.PROFILE_MOD_FLAG_KEY, 0);
                            newAcct.put(Const.PROFILE_REPUTATION_KEY, 0.0);
                            newAcct.put(Const.PROFILE_LATEST_LOGIN_DATE_KEY, new java.sql.Date(System.currentTimeMillis()).toString());
                            newAcct.put(Const.PROFILE_SUSPENDED_KEY, 0.0);
                        } catch (JSONException jse) {
                            Log.e("Creating JSON", jse.toString());
                        }

                        // create POST request to send to server
                        JsonObjectRequest postReq = new JsonObjectRequest(Request.Method.POST, Const.URL_POST_PROFILE, newAcct,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // if username of response is "&&&", there's already a user with the chosen username,
                                        // so let user know and don't continue
                                        try{
                                            if (response.getString(Const.PROFILE_USERNAME_KEY).equals("&&&")){
                                                Toast.makeText(getApplicationContext(), "Username unavailable", Toast.LENGTH_LONG).show();
                                                return;
                                            }
                                        } catch (JSONException jsone) {
                                            Log.d(Const.LOGTAG_JSONOBJECT_READ, "Could not extract username from JSON");
                                            Toast.makeText(getApplicationContext(), "An error occurred, please try again", Toast.LENGTH_LONG).show();
                                            return;
                                        }

                                        // otherwise, their credentials are valid and their username is available,
                                        // so notify user that their account has been created and:
                                        Toast.makeText(getApplicationContext(), "Account created :]", Toast.LENGTH_LONG).show();

                                        // 1: store profile information in SharedPrefs
                                        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                        prefEditor.putString(Const.SHAREDPREFS_USERNAME_KEY, username); // store username in SharedPrefs
                                        prefEditor.putInt(Const.SHAREDPREFS_USERTYPE_KEY, 0); // store usertype in SharedPrefs
                                        //TODO store more profile information as needed
                                        prefEditor.apply(); // apply changes to SharedPrefs

                                        // 2: start websocket as user
                                        GlobalState appState = (GlobalState) getApplicationContext();
                                        appState = (GlobalState) getApplicationContext();
                                        Log.d(Const.LOGTAG_WEBSOCKET, "Starting websocket with username: " + username); // log websocket start
                                        appState.startWebsocket(username); // start websocket

                                        //TODO change to account editing screen after it's been created
                                        // 3: switch to Account Editing Screen, passing username
                                        Intent changeScreen = new Intent(AccountRegistrationScreen.this, MainAppScreen.class);
                                        changeScreen.putExtra("PROFILE_USERNAME", username);
                                        startActivity(changeScreen);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Prof_POST_Req", error.toString());
                                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("Content-Type", "application/json");
                                return params;
                            }
                        };

                        // make POST request
                        reqQueue = Volley.newRequestQueue(getApplicationContext());
                        reqQueue.add(postReq);
                    }
                }
            }
        });
    }

    /**
     * Helper method that checks a user-input password against restrictions imposed to improve
     * account security and prevent bugs caused by certain characters or patterns in passwords
     * @param pwd the password input by the user
     * @param errorMsg used to create a message that lets the user know what's wrong with their password
     * @return returns true if the password is valid, false if not
     */
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
        final String specCharPattern = ".*(?=[@$!%*#?]+).*";
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
        final String restrCharPattern = ".*(?=[&=<>+,.]+).*";
        pattern = Pattern.compile(restrCharPattern);
        matcher = pattern.matcher(pwd);
        // if the matcher created from this pattern finds a match in the password,
        // then we know it contains a restricted character, so notify the user and return false
        if (matcher.matches()) {
            errorMsg.append("Password cannot contain restricted characters (=<>+,.)");
            return false;
        }

        // if no issues are found with the password, return true
        return true;
    }

    /**
     * Helper method that checks a user-input username against restrictions imposed to improve
     * account security and prevent bugs caused by certain characters or patterns in usernames
     * @param username the username input by the user
     * @param errorMsg used to create a message that lets the user know what's wrong with their username
     * @return true if the username is valid, false if not
     */
    private boolean usernameValidation(String username, StringBuilder errorMsg) {

        // pattern and matcher objects used to check for validity
        Pattern pattern;
        Matcher matcher;

        // clear errorMsg to prepare for appension
        errorMsg.delete(0, errorMsg.length());

        // check username length, notify user and return false if somehow longer than the limit
        if (username.length() > Const.USERNAME_CHARACTER_LIMIT) {
            errorMsg.append("Usernames cannot be longer than " + Const.USERNAME_CHARACTER_LIMIT + " characters");
            return false;
        }

        // make sure username doesn't contain any restricted characters (&=<>+,.)
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

        // if username is valid, return true
        return true;
    }
}