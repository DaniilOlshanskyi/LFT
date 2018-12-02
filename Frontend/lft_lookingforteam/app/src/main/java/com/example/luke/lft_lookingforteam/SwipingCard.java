package com.example.luke.lft_lookingforteam;

import java.util.ArrayList;

public class SwipingCard {

    private String username, availability, imageURL;
    private ArrayList<String> platforms, games;

    public SwipingCard (String username, String availability, String imageURL, ArrayList<String> platforms, ArrayList<String> games){
        this.username = username;
        this.availability = availability;
        this.imageURL = imageURL;
        this.platforms = platforms;
        this.games = games;
    }

    public String getUsername() {
        return username;
    }

    public String getAvailability(){
        return availability;
    }

    public String getImageURL() {
        return imageURL;
    }

    public ArrayList<String> getPlatforms() {
        return platforms;
    }

    public ArrayList<String> getGames() {
        return games;
    }
}
