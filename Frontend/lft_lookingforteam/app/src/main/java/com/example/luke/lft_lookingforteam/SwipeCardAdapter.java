package com.example.luke.lft_lookingforteam;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Custom ArrayAdapter class used to fill fields in swiping cards in the user interface from SwipingCard objects
 */
public class SwipeCardAdapter extends ArrayAdapter<SwipingCard> {

    /**
     * SwipeCardAdapter constructor. Calls its superconstructor
     * @param context Context object for this adapter
     * @param resource Resource ID of a view
     * @param cards ArrayList of SwipingCard objects
     */
    public SwipeCardAdapter(@NonNull Context context, int resource, ArrayList<SwipingCard> cards) {
        super(context, resource, cards);
    }

    /**
     * Sets up a View using the SwipingCard at the specified position in the array, an optional template View object, and a parent ViewGroup object
     * @param position position of SwipingCard
     * @param convertView optional template View
     * @param parent parent ViewGroup
     * @return returns the set-up View object
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.swipe_card, parent, false);
        }

        // get swiping card at position
        SwipingCard currentCard = getItem(position);

        // set username
        TextView username = listItem.findViewById(R.id.swipeCard_username);
        username.setText(currentCard.getUsername());

        // set availability
        TextView availability = listItem.findViewById(R.id.swipeCard_availability);
        availability.setText("Online: " + currentCard.getAvailability());

        // set profile picture
        ImageView profilePic = listItem.findViewById(R.id.swipeCard_profilePic);
        Glide.with(getContext()).load(currentCard.getImageURL()).into(profilePic);

        // TODO display platforms and games

        return listItem;
    }
}