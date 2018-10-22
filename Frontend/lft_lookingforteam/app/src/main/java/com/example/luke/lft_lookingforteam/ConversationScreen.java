package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ConversationScreen extends AppCompatActivity {

    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_screen);

        backButton = findViewById(R.id.conversationScreen_backButton);

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // go back to conversation list screen
                Intent i = new Intent(ConversationScreen.this, ConversationListScreen.class);
                startActivity(i);
            }
        });

        //TODO implement messaging stuff (message box, keyboard popping up, p2p messaging, etc)
    }
}
