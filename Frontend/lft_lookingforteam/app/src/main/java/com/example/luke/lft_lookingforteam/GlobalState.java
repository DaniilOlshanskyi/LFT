package com.example.luke.lft_lookingforteam;

import android.app.Application;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GlobalState extends Application {

    private static WebSocketClient websocket;
    private static JSONObject swipeProfileOnDeck;

    public static TextView chatWindow, user2;  // not ideal, work with ConversationScreen to update chatwindow

    // creates and starts a new websocket
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
                Log.d(Const.LOGTAG_WEBSOCKET_SWIPING_CARDS, "Requesting new swiping candidate...");
                // get initial swipe candidate
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
                    Log.d(Const.LOGTAG_WEBSOCKET_SWIPING_CARDS, "Received swiping profile for: " + contentElements[1] + ", creating JSON object");

                    // create JSON object
                    swipeProfileOnDeck = new JSONObject();
                    try{
                        swipeProfileOnDeck.put(Const.PROFILE_ID_KEY, contentElements[0]);
                        swipeProfileOnDeck.put(Const.PROFILE_USERNAME_KEY, contentElements[1]);
                        swipeProfileOnDeck.put(Const.PROFILE_PASSWORD_KEY, contentElements[2]);
                        swipeProfileOnDeck.put(Const.PROFILE_PERIOD_KEY, contentElements[3]);
                        swipeProfileOnDeck.put(Const.PROFILE_PHOTO_KEY, contentElements[4]);
                        swipeProfileOnDeck.put(Const.PROFILE_REPORT_FLAG_KEY, Integer.parseInt(contentElements[5]));
                        swipeProfileOnDeck.put(Const.PROFILE_MOD_FLAG_KEY, Integer.parseInt(contentElements[6]));
                        swipeProfileOnDeck.put(Const.PROFILE_REPUTATION_KEY, Float.parseFloat(contentElements[7]));
                        swipeProfileOnDeck.put(Const.PROFILE_LATEST_LOGIN_DATE_KEY, contentElements[8]);
                        swipeProfileOnDeck.put(Const.PROFILE_SUSPENDED_KEY, Float.parseFloat(contentElements[9]));
                    } catch (JSONException jsone){
                        // log error
                        Log.d(Const.LOGTAG_JSONOBJECT_CREATION, "Error creating JSONObject" + "\n\t" + jsone.toString());
                    }
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

                    // notify user
                    Toast.makeText(getApplicationContext(), "No more profiles to display", Toast.LENGTH_LONG).show();
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

    // closes websocket if not null
    public void closeWebsocket() {
        if (websocket != null) {
            websocket.close();
        }
    }

    // returns current websocket
    public WebSocketClient getWebsocket() {
        return websocket;
    }

    // returns the next swipe profile for displaying and asks the server for a new one
    public JSONObject getNextSwipeCandidate() {
        // log swipe profile request
        Log.d(Const.LOGTAG_WEBSOCKET_SWIPING_CARDS, "Requesting new swiping candidate...");
        websocket.send(Const.WEBSOCKET_SWIPING_TAG);
        return swipeProfileOnDeck;
    }
}