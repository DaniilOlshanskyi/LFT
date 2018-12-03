package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Manages other user profile view interface and operations
 */
public class ProfileViewScreen extends AppCompatActivity {

    String profileUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view_screen);

        // get username from intent
        Intent i = getIntent();
        profileUsername = i.getStringExtra(Const.INTENT_PROFILE_VIEW_USERNAME);

        //TODO GET profile from server and display information
    }
}
