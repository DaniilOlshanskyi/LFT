package com.example.luke.lft_lookingforteam;

import java.util.ArrayList;

/**
 * Custom class used to model the information needed for a swiping card
 */
public class SwipingCard {

    private String username, availability, imageURL;
    private ArrayList<String> platforms, games;

    /**
     * Constructor method
     * @param username username of user whose information is displayed on the card
     * @param availability availability of user
     * @param imageURL URL for user's profile picture stored on the server
     * @param platforms list of platforms the user plays games on
     * @param games list of games the user plays
     */
    public SwipingCard (String username, String availability, String imageURL, ArrayList<String> platforms, ArrayList<String> games){
        this.username = username;
        this.availability = availability;
        this.imageURL = imageURL;
        this.platforms = platforms;
        this.games = games;
    }

    /**
     * Returns user's username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns user's availability
     * @return availability
     */
    public String getAvailability(){
        return availability;
    }

    /**
     * Returns URL for user's profile picture
     * @return profile pic URL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Returns list of platforms user uses
     * @return list of platforms
     */
    public ArrayList<String> getPlatforms() {
        return platforms;
    }

    /**
     * Returns list of games user plays
     * @return list of games
     */
    public ArrayList<String> getGames() {
        return games;
    }
}
