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
    WebSocketClient chatSocket;
    GlobalState appState;
    String recipientUsername, myUsername;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_screen);

        // get user's username from shared prefs
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        myUsername = prefs.getString(Const.SHAREDPREFS_USERNAME_KEY, "me");

        // get recipient's username from intent
        Intent i = getIntent();
        recipientUsername = i.getStringExtra(Const.INTENT_CONVERSATION_USERNAME);

        // set username textView to recipient's username
        otherUserUsername = findViewById(R.id.conversationScreen_username);
        otherUserUsername.setText(recipientUsername);

        final File conversation = getConversationFile(recipientUsername);

        backButton = findViewById(R.id.conversationScreen_backButton);
        sendButton = findViewById(R.id.conversationScreen_sendButton);
        msgBox = findViewById(R.id.conversationScreen_messageBox);
        convoContent = findViewById(R.id.conversationScreen_conversationContent);

        appState = (GlobalState) getApplicationContext();
        chatSocket = appState.getChatClient();

        // display contents of convo file
        convoContent.setText("");
        try{
            String fileLine;
            String[] lineContents;
            String sender;
            String message;
            Scanner s = new Scanner(conversation);
            while (s.hasNextLine()) {
                fileLine = s.nextLine();
                lineContents = fileLine.split("@");
                sender = lineContents[0];
                message = lineContents[1];
                convoContent.append(sender + ": " + message);
            }
        } catch (IOException ioe) {
            //TODO log error
        }

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // go back to conversation list screen
                Intent i = new Intent(ConversationScreen.this, ConversationListScreen.class);
                startActivity(i);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSend = msgBox.getText().toString();
                chatSocket.send("m:" + recipientUsername + "@" + toSend);

                // add message to conversation file
                try {
                    FileWriter fw = new FileWriter(conversation);
                    fw.append(myUsername + "@" + toSend + "\n");
                } catch (IOException ioe) {
                    //TODO log error
                }

                // clear message box
                msgBox.setText("");
            }
        });
    }

    private File getConversationFile(String filename){
        File toReturn = new File("empty");
        File fileDir = getDir("conversation_files", MODE_PRIVATE);   // get file directory for conversation files
        File[] fileList = fileDir.listFiles();  // get list of conversation files

        // find desired conversation file
        for (File f : fileList) {
            if (f.getName().equals(filename)) {
                toReturn = f;
                break;
            }
        }

        return toReturn;
    }
}