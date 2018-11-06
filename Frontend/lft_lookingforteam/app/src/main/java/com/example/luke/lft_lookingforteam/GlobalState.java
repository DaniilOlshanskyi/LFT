package com.example.luke.lft_lookingforteam;

import android.app.Application;
import android.util.Log;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GlobalState extends Application {

    private static WebSocketClient chatSocket;

    public static TextView chatWindow, user2;  // not ideal, work with ConversationScreen to update chatwindow

    // creates and starts a new chatSocket
    public void startChatClient(String currentUser) {
        // create URI for websocket creation
        URI uri;
        try {
            uri = new URI(Const.URL_OPEN_WEBSOCKET + currentUser);
        } catch (URISyntaxException uriSE) {
            // if, somehow, something goes wrong when creating this, return and...
            //TODO log it
            return;
        }

        // instantiate websocket from URI
        chatSocket = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                // log get request
                Log.d(Const.LOGTAG_CHAT_WEBSOCKET, "Requesting cached messages...");

                // request cached messages
                chatSocket.send("g:");
            }

            @Override
            public void onMessage(String s) {
                String senderUsername;
                String message;

                // parse message for contents
                String[] messageElements = s.split("@");
                senderUsername = messageElements[0];
                message = messageElements[1];

                // log message received
                Log.d(Const.LOGTAG_CHAT_WEBSOCKET, "Message Received: " + s + "\n\t" + "Sender: " + senderUsername + ", Message: " + message + "\n");

                // update conversation file
                // iterate over conversation files
                boolean filefound = false;
                File fileDir = getDir("conversation_files", MODE_PRIVATE);
                File[] fileList = fileDir.listFiles();
                for (File f : fileList) {
                    // find conversation file for sender,
                    if (f.getName().equals(senderUsername + ".txt")) {
                        // and add received message to it
                        try {
                            FileWriter fw = new FileWriter(f, true);
                            fw.write(senderUsername + ": " + message + "\n");
                            fw.close();
                        } catch (IOException ioe) {
                            // log error
                            Log.d(Const.LOGTAG_FILE_WRITE, "Error writing message to file: " + f.toString() + "\n\t" + ioe.toString());
                        }
                        // file found, so exit loop
                        filefound = true;
                        break;
                    }
                }

                // if file not found, create one and add message
                if (!filefound){
                    File newFile = new File(fileDir, senderUsername + ".txt");
                    try{
                        FileWriter fw = new FileWriter(newFile, true);
                        fw.write(senderUsername + ": " + message + "\n");
                    } catch (IOException ioe) {
                        // log error
                        Log.d(Const.LOGTAG_FILE_WRITE, "Error writing message to file: " + newFile.toString() + "\n\t" + ioe.toString());
                    }
                }

                // if Conversation Screen is active with same username as sender, update chat window
                if (chatWindow != null && user2 != null && user2.getText().equals(senderUsername)) {
                    chatWindow.append(senderUsername + ": " + message + "\n");
                }

                //TODO set "new message" icon flag
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.d(Const.LOGTAG_CHAT_WEBSOCKET, "Websocket closed");
            }

            @Override
            public void onError(Exception e) {
                // log error
                Log.d(Const.LOGTAG_CHAT_WEBSOCKET, "Error: " + e.toString());
            }
        };
        chatSocket.connect();
    }

    // closes chatSocket if not null
    public void closeChatClient() {
        if (chatSocket != null) {
            chatSocket.close();
        }
    }

    // returns current chatSocket
    public WebSocketClient getChatClient() {
        return chatSocket;
    }
}