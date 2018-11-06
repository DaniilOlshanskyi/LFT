package com.example.luke.lft_lookingforteam;

import android.app.Application;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GlobalState extends Application {

    private static WebSocketClient chatSocket;

    public void startChatClient(String currentUser) {
        URI uri;
        try {
            uri = new URI(Const.URL_OPEN_WEBSOCKET + currentUser);
        } catch (URISyntaxException uriSE) {
            // if, somehow, something goes wrong when creating this, return and...
            //TODO log it
            return;
        }

        chatSocket = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
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

                // update conversation file
                // iterate over conversation files
                File fileDir = getDir("conversation_files", MODE_PRIVATE);
                File[] fileList = fileDir.listFiles();
                for (File f : fileList) {
                    // find conversation file for sender,
                    if (f.getName().equals(senderUsername + ".txt")) {
                        // and add received message to it
                        try {
                            FileWriter fw = new FileWriter(f, true);
                            fw.append(senderUsername + ": " + message + "\n");
                            fw.close();
                        } catch (IOException ioe) {
                            //TODO log it
                        }
                        // file found, so exit loop
                        break;
                    }
                }

                //TODO set "new message" icon flag
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                //TODO log it
            }

            @Override
            public void onError(Exception e) {
                //TODO log it
            }
        };
        chatSocket.connect();
    }

    public void closeChatClient(){
        if (chatSocket != null){
            chatSocket.close();
        }
    }

    public WebSocketClient getChatClient() {
        return chatSocket;
    }
}
