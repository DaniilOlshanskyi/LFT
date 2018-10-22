package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ModSwipeScreen extends AppCompatActivity {

    Button myProfileButton, swipeScreenButton, messagingButton, viewProfileButton, connectButton, passButton, reportsButton;
    ImageButton swipeSettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mod_swipe_screen);

        myProfileButton = findViewById(R.id.modSwipeScreen_myProfileButton);
        swipeScreenButton = findViewById(R.id.modSwipeScreen_swipeScreenButton);
        messagingButton = findViewById(R.id.modSwipeScreen_messagingButton);
        viewProfileButton = findViewById(R.id.modSwipeScreen_viewProfileButton);
        connectButton = findViewById(R.id.modSwipeScreen_connectButton);
        passButton = findViewById(R.id.modSwipeScreen_passButton);
        swipeSettingsButton = findViewById(R.id.modSwipeScreen_swipeSettingsButton);
        reportsButton = findViewById(R.id.modSwipeScreen_reportsButton);

        //TODO highlight swipeScreenButton to show that it's the active screen. Also disable it from being clickable if possible

        // when the "My Profile" button is pressed
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to account view screen
                Intent i = new Intent(ModSwipeScreen.this, MyProfileViewScreen.class);
                startActivity(i);
            }
        });

        // when the "Messaging" button is pressed
        messagingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to messaging screen
                Intent i = new Intent(ModSwipeScreen.this, MessagingScreen.class);
                startActivity(i);
            }
        });

        // when the "View 'username's" Profile" button is pressed
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to profile view screen
                Intent i = new Intent(ModSwipeScreen.this, ProfileViewScreen.class);
                //TODO pass username of account being viewed
                startActivity(i);
            }
        });

        // when the "Connect" button is pressed
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO attempt to "match" with account being viewed
                //TODO show next candidate
            }
        });

        // when the "Pass" button is pressed
        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO deny match with account being viewed
                //TODO show next candidate
            }
        });

        // when the settings button is pressed
        swipeSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO pop-up layout with swipe settings
            }
        });

        // when the reports button is pressed
        reportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO go to reports page
            }
        });
    }
}
