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

import java.util.List;

public class ConvoCardAdapter extends ArrayAdapter<ConversationCard> {

    public ConvoCardAdapter(@NonNull Context context, int resource, @NonNull List<ConversationCard> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.conversation_card, parent, false);
        }

        ConversationCard currentCard = getItem(position);

        ImageView profilePic = listItem.findViewById(R.id.conversationCard_profilePic);
        Glide.with(getContext()).load(Const.URL_PROFILE_PICTURES + currentCard.getImageURL()).into(profilePic);

        TextView username = listItem.findViewById(R.id.conversationCard_username);
        username.setText(currentCard.getUsername());

        TextView lastMsg = listItem.findViewById(R.id.conversationCard_lastMsg);
        lastMsg.setText(currentCard.getLastMsg());

        return listItem;
    }
}
