package com.example.luke.lft_lookingforteam;

import android.app.Application;

import org.java_websocket.client.WebSocketClient;

public class GlobalState extends Application {

    private WebSocketClient chatClient;

    public void setChatClient(WebSocketClient newClient) {
        chatClient = newClient;
    }

    public WebSocketClient getChatClient() {
        return chatClient;
    }
}
