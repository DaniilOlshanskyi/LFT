package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConversationListScreen extends AppCompatActivity {

    Button myProfileButton, swipeScreenButton, messagingButton;
    ConstraintLayout conversationCardContainer;
    Button conversationCard;
    int usertype;
    Intent changeScreen;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_list_screen);

        // get usertype
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        usertype = prefs.getInt(Const.SHAREDPREFS_USERTYPE_KEY, Const.USERTYPE_BASIC_USER);

        myProfileButton = findViewById(R.id.conversationListScreen_myProfileButton);
        swipeScreenButton = findViewById(R.id.conversationListScreen_swipeScreenButton);
        messagingButton = findViewById(R.id.conversationListScreen_messagingButton);

        //TODO highlight "messaging" button and disable it from being pressed

        //TODO figure out how to create and display clickable conversation cards

        // when the "My Profile" button is pressed
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to account view screen
                changeScreen = new Intent(ConversationListScreen.this, MyProfileViewScreen.class);
                startActivity(changeScreen);
            }
        });

        // when the "Swiping" button is pressed
        swipeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to appropriate swipe screen
                if (usertype == Const.USERTYPE_BASIC_USER) {
                    changeScreen = new Intent(ConversationListScreen.this, UserSwipeScreen.class);
                } else if (usertype == Const.USERTYPE_MODERATOR) {
                    changeScreen = new Intent(ConversationListScreen.this, ModSwipeScreen.class);
                } else if (usertype == Const.USERTYPE_ADMIN) {
                    changeScreen = new Intent(ConversationListScreen.this, AdminSwipeScreen.class);
                } else {
                    // if something goes wrong and usertype doesn't match any defined types, default to basic user
                    changeScreen = new Intent(ConversationListScreen.this, UserSwipeScreen.class);
                }
                startActivity(changeScreen);
            }
        });

        // set click listener for each conversation card
        conversationCardContainer = findViewById(R.id.conversationListScreen_conversationList);
        for (int i = 0; i < conversationCardContainer.getChildCount(); i++) {
            conversationCard = (Button) conversationCardContainer.getChildAt(i);

            //TODO delete this testing code
            conversationCard.setText("test" + i);
            conversationCard.setContentDescription("test" + i);
            //end of testing code

            conversationCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // switch to conversation screen, passing username of other user
                    changeScreen = new Intent(ConversationListScreen.this, ConversationScreen.class);
                    changeScreen.putExtra(Const.INTENT_CONVERSATION_USERNAME, conversationCard.getContentDescription().toString());
                    startActivity(changeScreen);
                }
            });
        }
    }
}
