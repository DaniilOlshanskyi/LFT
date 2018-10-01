package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountRegistrationScreen extends AppCompatActivity {

    Button submitButton;
    EditText usernameField, passwordField;
    String username, password;
    StringBuilder pwdErrorMsg;     // used to tell user what's wrong with their password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_registration_screen);

        submitButton = findViewById(R.id.acctRegSubmitButton);
        usernameField = findViewById(R.id.accountCreation_username);
        passwordField = findViewById(R.id.accountCreation_password);

        // when the "submit" button is pressed
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get strings from text typed into "username" and "password" fields
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();

                // check to make sure username isn't already in use
                if (!usernameAvailable(username)) {
                    // if username is unavailable, tell user
                    Toast.makeText(getApplicationContext(), "Username unavailable", Toast.LENGTH_LONG).show();
                } else {
                    // check whether password is valid, and tell user if it's invalid
                    if (!passwordValidation(password, pwdErrorMsg)) {
                        Toast.makeText(getApplicationContext(), pwdErrorMsg, Toast.LENGTH_LONG).show();
                    }
                    // if it's valid, continue
                    else {
                        // TODO send profile creation request to server
                    }
                }
            }
        });
    }

    // password validation method
    private boolean passwordValidation(String pwd, StringBuilder errorMsg) {

        // clear errorMsg
        errorMsg.delete(0, errorMsg.length());

        // check password length
        if (pwd.length() < 8) {
            errorMsg.append("Password is too short");
            return false;
        }

        // TODO include more password-checking

        // not really sure what this does
//        Pattern pattern;
//        Matcher matcher;
//        final String PWD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[$@$!%*#?&])(?=\\S+$).{4,}$";
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
