package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//TODO finish layout design and implement login code

public class LoginScreen extends AppCompatActivity {

    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        loginButton = findViewById(R.id.loginLoginButton);
        registerButton = findViewById(R.id.loginRegisterButton);

        // when the "login" button is pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO attempt to log user in

                // upon successful login, switch to swipe screen
                Intent i = new Intent(LoginScreen.this, UserSwipeScreen.class);
                //TODO pass username
                startActivity(i);
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
}
