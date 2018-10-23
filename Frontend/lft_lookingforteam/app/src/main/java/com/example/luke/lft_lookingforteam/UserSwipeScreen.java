package com.example.luke.lft_lookingforteam;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

public class UserSwipeScreen extends AppCompatActivity {

    Button myProfileButton, swipeScreenButton, messagingButton, viewProfileButton, connectButton, passButton;
    ImageButton swipeSettingsButton;
    PopupWindow swipeSettingsWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_swipe_screen);

        myProfileButton = findViewById(R.id.userSwipeScreen_myProfileButton);
        swipeScreenButton = findViewById(R.id.userSwipeScreen_swipeScreenButton);
        messagingButton = findViewById(R.id.userSwipeScreen_messagingButton);
        viewProfileButton = findViewById(R.id.userSwipeScreen_viewProfileButton);
        connectButton = findViewById(R.id.userSwipeScreen_connectButton);
        passButton = findViewById(R.id.userSwipeScreen_passButton);
        swipeSettingsButton = findViewById(R.id.userSwipeScreen_swipeSettingsButton);

        //TODO highlight swipeScreenButton to show that it's the active screen. Also disable it from being clickable if possible

        //TODO start showing profile swipe cards

        // when the "My Profile" button is pressed
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to account view screen
                Intent i = new Intent(UserSwipeScreen.this, MyProfileViewScreen.class);
                startActivity(i);
            }
        });

        // when the "Messaging" button is pressed
        messagingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to messaging screen
                Intent i = new Intent(UserSwipeScreen.this, ConversationListScreen.class);
                startActivity(i);
            }
        });

        // when the "View 'username's" Profile" button is pressed
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to profile view screen
                Intent i = new Intent(UserSwipeScreen.this, ProfileViewScreen.class);
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
                ShowSwipeSettings();
            }
        });
    }

    // set as onClick method for swipeSettingsButtons
    public void ShowSwipeSettings() {
        ImageButton closeSettingsButton;
        Button applySettingsButton;

        LayoutInflater inf = (LayoutInflater) UserSwipeScreen.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inf.inflate(R.layout.swipe_settings_popup, (ViewGroup) findViewById(R.id.swipeSettingsPopup_1));
        swipeSettingsWindow = new PopupWindow(layout, 1000, 1800, true);
        swipeSettingsWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        closeSettingsButton = findViewById(R.id.swipeSettings_closeButton);
        applySettingsButton = findViewById(R.id.swipeSettings_applyChangesButton);

        // when "X" button is pressed
//        closeSettingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // close settings page, discarding changes
//                swipeSettingsWindow.dismiss();
//            }
//        });
//
        // when "Apply Settings" button is pressed
//        applySettingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // apply settings
//                //TODO change settings based on new values
//
//                // fetch new candidate profiles
//                //TODO get new candidate profiles based on new settings
//
//                // close settings page
//                swipeSettingsWindow.dismiss();
//            }
//        });
    }
}