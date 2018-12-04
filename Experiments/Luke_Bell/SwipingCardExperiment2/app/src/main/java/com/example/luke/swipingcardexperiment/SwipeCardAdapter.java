package com.example.luke.swipingcardexperiment;

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

import java.util.List;

public class SwipeCardAdapter extends ArrayAdapter<SwipeCard> {

    private int resource;

    protected SwipeCardAdapter(@NonNull Context context, int resource, @NonNull List<SwipeCard> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.swipe_card, parent, false);
        }

        SwipeCard currentCard = getItem(position);

        TextView username = listItem.findViewById(R.id.swipeCard_username);
        username.setText(currentCard.getUsername());

        TextView availability = listItem.findViewById(R.id.swipeCard_availability);
        availability.setText("Online: " + currentCard.getAvailability());

        ImageView profilePic = listItem.findViewById(R.id.swipeCard_profilePic);
        Glide.with(getContext()).load(currentCard.getProfilePicURL()).into(profilePic);

        //TODO implement platform and game icons

        return listItem;
    }
}
