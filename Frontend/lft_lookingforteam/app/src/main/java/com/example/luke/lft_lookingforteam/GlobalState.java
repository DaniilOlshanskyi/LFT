package com.example.luke.lft_lookingforteam;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom Application class used to provide access to data/services needed across multiple activities within the application
 */
public class GlobalState extends Application {

    private static WebSocketClient websocket;
    private static List<SwipingCard> swipeCards;
    private static Context appContext;

    public static TextView chatWindow, user2;  // not ideal, work with ConversationScreen to update chatwindow

    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        swipeCards = new ArrayList<>();
    }

    /**
     * Allows for static access of the application context
     * @return returns the application context
     */
    public static Context getAppContext() {
        return appContext;
    }

    /**
     * Creates and connects a new websocket that handles messaging and swiping/matching services
     * @param currentUser username of user currently logged in
     */
    public void startWebsocket(String currentUser) {
        // create URI for websocket creation
        URI uri;
        try {
            uri = new URI(Const.URL_OPEN_WEBSOCKET + currentUser);
        } catch (URISyntaxException uriSE) {
            // if, somehow, something goes wrong when creating this, log error and return
            Log.d(Const.LOGTAG_WEBSOCKET_CREATION, "Websocket URI error: " + uriSE.toString());
            return;
        }

        // instantiate websocket from URI
        websocket = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                // log websocket created
                Log.d(Const.LOGTAG_WEBSOCKET_CREATION, "Websocket opened");

                // log cached messages request
                Log.d(Const.LOGTAG_WEBSOCKET_CHAT, "Requesting cached messages...");
                // request cached messages
                websocket.send(Const.WEBSOCKET_CHAT_CACHE_TAG);

                // log swipe profile request
                Log.d(Const.LOGTAG_WEBSOCKET_SWIPING_CARDS, "Requesting 3 initial swiping candidates...");
                // get initial swipe candidates (3)
                websocket.send(Const.WEBSOCKET_SWIPING_TAG);
                websocket.send(Const.WEBSOCKET_SWIPING_TAG);
                websocket.send(Const.WEBSOCKET_SWIPING_TAG);
            }

            @Override
            public void onMessage(String s) {
                String messageType;
                String messageContent;
                String[] contentElements;

                // parse message for type and content
                messageType = s.substring(0,1);
                messageContent = s.substring(2, s.length());

                // perform operations based on message type

                // if chat message
                if (messageType.equals(Const.WEBSOCKET_CHAT_TAG)){
                    String senderUsername;
                    String message;

                    //TODO implement for multiple cached messages

                    // parse message for contents
                    contentElements = messageContent.split(Const.WEBSOCKET_DATA_SEPARATOR);
                    senderUsername = contentElements[0];
                    message = contentElements[1];

                    // log message received
                    Log.d(Const.LOGTAG_WEBSOCKET_CHAT, "Message Received: " + "Sender: " + senderUsername + ", Message: " + message);

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
                            fw.close();
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

                // if swiping list message
                else if (messageType.equals(Const.WEBSOCKET_SWIPING_TAG)){
                    // extract profile data from message
                    contentElements = messageContent.split(Const.WEBSOCKET_DATA_SEPARATOR);

                    // log swipe candidate received
                    Log.d(Const.LOGTAG_WEBSOCKET_SWIPING_CARDS, "Received swiping profile for: " + contentElements[1] + ", creating swiping card");

                    // create swiping card object and add it to the list
                    SwipingCard newCard = new SwipingCard (contentElements[1], contentElements[3], contentElements[4], new ArrayList<String>(), new ArrayList<String>());
                    swipeCards.add(newCard);
                }

                // if match message
                else if (messageType.equals(Const.WEBSOCKET_MATCHING_TAG)){
                    // log match received
                    Log.d(Const.LOGTAG_WEBSOCKET_SWIPING_MATCH, "Matched with user: " + messageContent + ", creating conversation file");

                    // message content should be username of matched user,
                    // so start conversation with them
                    File fileDir = getDir("conversation_files", MODE_PRIVATE);
                    File newFile = new File(fileDir, messageContent + ".txt");
                    try{
                        newFile.createNewFile();
                    } catch (IOException ioe){
                        // log error
                        Log.d(Const.LOGTAG_FILE_CREATE, "Error creating file: " + newFile.toString() + "\n\t" + ioe.toString());
                    }

                    // notify user of match
                    Toast.makeText(getApplicationContext(), "You matched with " + messageContent + ", go to messaging screen to start chatting", Toast.LENGTH_LONG).show();
                }

                // if server swiping queue for user is empty
                else if (messageType.equals(Const.WEBSOCKET_EMPTY_TAG)){
                    // log empty swiping queue
                    Log.d(Const.LOGTAG_WEBSOCKET_SWIPING_CARDS, "Swiping queue empty");
                }

                // insert more message type cases as needed
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.d(Const.LOGTAG_WEBSOCKET_CREATION, "Websocket closed");
            }

            @Override
            public void onError(Exception e) {
                // log error
                Log.d(Const.LOGTAG_WEBSOCKET_ERROR, e.toString());
            }
        };
        websocket.connect();
    }

    /**
     * Closes the current websocket, if one is open
     */
    public void closeWebsocket() {
        if (websocket != null) {
            websocket.close();
        }
    }

    /**
     * Allows for static access of the websocket across the application
     * @return returns the websocket
     */
    public static WebSocketClient getWebsocket() {
        return websocket;
    }

    /**
     * Returns a SwipingCard object from the list, and requests a new swiping candidate from the server via websocket
     * @return returns the SwipingCard at the bottom of the list
     */
    public SwipingCard getSwipeCandidate() {
        // log swipe profile request
        Log.d(Const.LOGTAG_WEBSOCKET_SWIPING_CARDS, "Requesting new swiping candidate...");
        websocket.send(Const.WEBSOCKET_SWIPING_TAG);

        // return the next swipe card
        return swipeCards.remove(0);
    }
}