package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Displays the opening screen of the app while performing startup operations
 */
public class AppOpeningScreen extends AppCompatActivity {

    GlobalState appState;
    Intent switchScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_opening_screen);

        // get SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // attempt to get username from SharedPreferences
        if (prefs.getString(Const.SHAREDPREFS_USERNAME_KEY, null) == null) {
            // if it's null, no user is logged-in, so go to login screen
            switchScreen = new Intent(AppOpeningScreen.this, LoginScreen.class);
            startActivity(switchScreen);
        } else {
            // if a non-null value is found for username, there must be a user logged-in:
            // 1: start websocket with username
            String username = prefs.getString(Const.SHAREDPREFS_USERNAME_KEY, "");
            appState = (GlobalState) getApplicationContext();
            Log.d(Const.LOGTAG_WEBSOCKET, "Starting websocket with username: " + username); // log websocket start
            appState.startWebsocket(username); // start websocket

            // 2: go to the main page
            switchScreen = new Intent(AppOpeningScreen.this, MainAppScreen.class);
            startActivity(switchScreen);
        }
    }
}