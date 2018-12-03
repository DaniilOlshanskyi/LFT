package com.example.luke.lft_lookingforteam;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom ArrayAdapter class used to fill fields in swiping cards in the user interface from SwipingCard objects
 */
public class SwipeCardAdapter extends ArrayAdapter<SwipingCard> {
    private Context context;
    private List<SwipingCard> cards;

    /**
     * SwipeCardAdapter constructor. Calls its superconstructor
     * @param context Context object for this adapter
     * @param resource Resource ID of a view
     * @param cards ArrayList of SwipingCard objects
     */
    public SwipeCardAdapter(@NonNull Context context, int resource, ArrayList<SwipingCard> cards) {
        super(context, resource, cards);
        this.context = context;
        this.cards = cards;
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
            listItem = LayoutInflater.from(context).inflate(R.layout.swipe_card, parent, false);
        }

        // get swiping card at position
        SwipingCard currentCard = cards.get(position);

        // set username
        TextView username = listItem.findViewById(R.id.swipeCard_username);
        username.setText(currentCard.getUsername());

        // set availability
        TextView availability = listItem.findViewById(R.id.swipeCard_availability);
        availability.setText("Online: " + currentCard.getAvailability());

        // TODO get and set image from URL, and display platforms and games

        return listItem;
    }
}