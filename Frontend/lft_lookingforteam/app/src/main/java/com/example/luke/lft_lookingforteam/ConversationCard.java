package com.example.luke.lft_lookingforteam;

public class ConversationCard {

    private String username, lastMsg, imageURL;

    public ConversationCard(String username, String lastMsg, String imageURL) {
        this.username = username;
        this.lastMsg = lastMsg;
        this.imageURL = imageURL;
    }

    public String getUsername() {
        return username;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getImageURL() {
        return imageURL;
    }
}
