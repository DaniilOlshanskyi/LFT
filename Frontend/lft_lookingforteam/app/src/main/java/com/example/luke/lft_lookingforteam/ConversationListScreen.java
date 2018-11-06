package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConversationListScreen extends AppCompatActivity {

    Button testConversationCard;

    Button myProfileButton, swipeScreenButton, messagingButton;
    LinearLayout conversationCardList;
    Intent changeScreen;
    SharedPreferences prefs;
    int usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_list_screen);


        testConversationCard = findViewById(R.id.conversationListScreen_testButton);
        testConversationCard.setContentDescription("test1");
        testConversationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeScreen = new Intent(ConversationListScreen.this, ConversationScreen.class);
                changeScreen.putExtra(Const.INTENT_CONVERSATION_USERNAME, testConversationCard.getContentDescription().toString());
                startActivity(changeScreen);
            }
        });


        // get usertype
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        usertype = prefs.getInt(Const.SHAREDPREFS_USERTYPE_KEY, Const.USERTYPE_BASIC_USER);

        // reference layout objects
        conversationCardList = findViewById(R.id.conversationListScreen_conversationList);
        myProfileButton = findViewById(R.id.conversationListScreen_myProfileButton);
        swipeScreenButton = findViewById(R.id.conversationListScreen_swipeScreenButton);
        messagingButton = findViewById(R.id.conversationListScreen_messagingButton);

        //TODO highlight "messaging" button and disable it from being pressed

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

        // create conversation card button for each conversation file in conversation files directory
        // 1: get all conversation files
        File convoFilesDir = getDir("conversation_files", MODE_PRIVATE);
        File[] convoFileList = convoFilesDir.listFiles();

        // 2: iterate over conversation files, creating a conversation card for each one and adding it to the LinearLayout
        for (int f = 0; f < convoFileList.length; f++) {
            // for each file:
            File file = convoFileList[f];
            // 1: get recipient's username from filename (should be first array element)
            String[] filenameContents = file.getName().split(".txt");
            // if filenameContents[0] doesn't exist, the file being read isn't a conversation file, so skip it
            if (filenameContents.length < 1) {
                continue;
            }
            String recipientUsername = filenameContents[0];

            // 2: get most recent message from conversation (if one exists)
            String recentMsg = "";  // most recent message in convo, default to empty
            String lastLine = "";   // last line of file, default to empty
            try {
                // open file and read until last line is hit (if file is empty, lastLine will remain empty)
                String currentLine;
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                while ((currentLine = fileReader.readLine()) != null) {
                    lastLine = currentLine;
                }
            } catch (IOException ioe) {
                //TODO log it
            }

            // 3: extract message from last line of file
            String[] latestMsgContents = lastLine.split(": ");
            if (latestMsgContents.length >= 2) {
                recentMsg = latestMsgContents[1];
            }

            // 4: create new conversation card button, and set its attributes
            final Button newConvoCard = new Button(this);
            String toSet = recipientUsername + "\n" + recentMsg;
            newConvoCard.setText(toSet);
            newConvoCard.setContentDescription(recipientUsername);
            newConvoCard.setId(f);
            newConvoCard.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    80
            ));
            //TODO add more attributes

            // 5: set click listener for convo card
            newConvoCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeScreen = new Intent(ConversationListScreen.this, ConversationScreen.class);
                    changeScreen.putExtra(Const.INTENT_CONVERSATION_USERNAME, v.getContentDescription().toString());
                    startActivity(changeScreen);
                }
            });

            // 6: add convo card to LinearLayout
            conversationCardList.addView(newConvoCard);
        }
    }

    // sets params for a conversation card being created
    // uses "parent" parameter to set width and horizontal constraints
    // uses "topConstraint" parameter to set vertical constraint
    private Button setConversationCardParams(Button convoCard, View parent, View topConstraint) {
        //TODO modify convoCard params

        return convoCard;
    }
}
