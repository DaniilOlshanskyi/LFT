package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConversationListScreen extends AppCompatActivity {

    Button myProfileButton, swipeScreenButton, messagingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_list_screen);

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
                Intent i = new Intent(ConversationListScreen.this, MyProfileViewScreen.class);
                startActivity(i);
            }
        });

        // when the "Swiping" button is pressed
        swipeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to appropriate swipe screen
                Intent i = new Intent(ConversationListScreen.this, UserSwipeScreen.class);
                startActivity(i);

                //TODO implement mod and admin swipe screen switching
            }
        });
    }
}
