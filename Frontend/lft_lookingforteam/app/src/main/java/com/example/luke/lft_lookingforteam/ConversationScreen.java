package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConversationScreen extends AppCompatActivity {

    ImageButton backButton;
    Button sendButton;
    EditText msgBox;
    TextView otherUserUsername, convoContent;
    GlobalState appState;
    String recipientUsername, myUsername;
    SharedPreferences prefs;
    Intent changeScreen;
    FileWriter convoWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_screen);

        // reference layout items
        backButton = findViewById(R.id.conversationScreen_backButton);
        sendButton = findViewById(R.id.conversationScreen_sendButton);
        msgBox = findViewById(R.id.conversationScreen_messageBox);
        convoContent = findViewById(R.id.conversationScreen_conversationContent);

        // get Global State for application
        appState = (GlobalState) getApplicationContext();

        // get user's username from shared prefs
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        myUsername = prefs.getString(Const.SHAREDPREFS_USERNAME_KEY, "me");

        // get recipient's username from intent
        Intent i = getIntent();
        recipientUsername = i.getStringExtra(Const.INTENT_CONVERSATION_USERNAME);

        // set username textView to recipient's username
        otherUserUsername = findViewById(R.id.conversationScreen_username);
        otherUserUsername.setText(recipientUsername);

        // get convo file for recipient and instantiate FileWriter
        final File conversation = getConversationFile(recipientUsername + ".txt");
        try{
            convoWriter = new FileWriter(conversation);
        } catch (IOException ioe) {
            //TODO log it
        }

        // display contents of convo file, line by line
        convoContent.setText("");
        try {
            Scanner s = new Scanner(conversation);
            while (s.hasNextLine()) {
                convoContent.append(s.nextLine());
            }
            s.close();
        } catch (IOException ioe) {
            //TODO log error
        }

        // when "Back" button is pressed
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to conversation list screen
                changeScreen = new Intent(ConversationScreen.this, ConversationListScreen.class);
                startActivity(changeScreen);
            }
        });

        // when "Send" button is pressed
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSend = msgBox.getText().toString();
                appState.getChatClient().send("m:" + recipientUsername + "@" + toSend);

                // add message to conversation file
                try {
                    convoWriter.append("Me: " + toSend + "\n");
                } catch (IOException ioe) {
                    //TODO log error
                }

                // add message to conversation box
                convoContent.append("Me: " + toSend);

                // clear message box
                msgBox.setText("");
            }
        });
    }

    private File getConversationFile(String filename) {
        // get all conversation files
        File fileDir = getDir("conversation_files", MODE_PRIVATE);
        File[] fileList = fileDir.listFiles();

        // get desired conversation file from list and return it
        for (File f : fileList) {
            if (f.getName().equals(filename)) {
                return f;
            }
        }

        // if file isn't found, create new empty file inside conversations directory and return it
        return new File(fileDir, filename);
    }
}