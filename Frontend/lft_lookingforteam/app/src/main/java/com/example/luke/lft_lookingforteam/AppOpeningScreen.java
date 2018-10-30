package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.luke.lft_lookingforteam.net_utils.Const;

public class AppOpeningScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_opening_screen);

        // get SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // attempt to get username from SharedPreferences
        if (prefs.getString(Const.SHAREDPREFS_USERNAME_KEY, null) == null) {
            // if it's null, no user is logged-in, so go to login screen
            Intent i = new Intent(AppOpeningScreen.this, LoginScreen.class);
            startActivity(i);
        } else {
            // if a non-null value is found for username, there must be a user logged-in, so go to the swiping page based on usertype
            int usertype = prefs.getInt(Const.SHAREDPREFS_USERTYPE_KEY, Const.USERTYPE_BASIC_USER);

            if (usertype == Const.USERTYPE_BASIC_USER) {
                Intent i = new Intent(AppOpeningScreen.this, UserSwipeScreen.class);
                startActivity(i);
            } else if (usertype == Const.USERTYPE_MODERATOR) {
                Intent i = new Intent(AppOpeningScreen.this, ModSwipeScreen.class);
                startActivity(i);
            } else if (usertype == Const.USERTYPE_ADMIN) {
                Intent i = new Intent(AppOpeningScreen.this, AdminSwipeScreen.class);
                startActivity(i);
            }
        }
    }
}