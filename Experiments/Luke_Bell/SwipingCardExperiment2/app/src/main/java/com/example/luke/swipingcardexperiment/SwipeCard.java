package com.example.luke.swipingcardexperiment;

import java.util.ArrayList;

public class SwipeCard {

    private String username, availability, profilePicURL;
    private ArrayList<String> platforms, games;

    protected SwipeCard (String username, String availability, String profilePicURL, ArrayList<String> platforms, ArrayList<String> games){
        this.username = username;
        this.availability = availability;
        this.profilePicURL = profilePicURL;
        this.platforms = platforms;
        this.games = games;
    }

    protected String getUsername() {
        return username;
    }

    protected String getAvailability() {
        return availability;
    }

    protected String getProfilePicURL() {
        return profilePicURL;
    }

    protected ArrayList<String> getPlatforms() {
        return platforms;
    }

    protected ArrayList<String> getGames() {
        return games;
    }
}
