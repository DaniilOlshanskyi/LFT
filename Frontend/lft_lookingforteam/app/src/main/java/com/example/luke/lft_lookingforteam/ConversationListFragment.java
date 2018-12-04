package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.java_websocket.client.WebSocketClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Fragment class that manages the conversation list interface and operations
 */
public class ConversationListFragment extends Fragment {

    private ArrayList<ConversationCard> cards;
    private ConvoCardAdapter cardAdapter;
    private ListView cardListView;

    private Intent screenSwitch;
    private WebSocketClient websocket;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversation_list_screen, container, false);

        cards = new ArrayList<>();

        // iterate over conversation files, creating conversation cards for each
        File fileDir = GlobalState.getAppContext().getDir("conversation_files", MODE_PRIVATE);
        for (File f : fileDir.listFiles()) {
            String username = f.getName().split(".")[0];

            String lastLine = "";
            String currentLine;
            try{
                BufferedReader input = new BufferedReader(new FileReader(f));

                while ((currentLine = input.readLine()) != null){
                    lastLine = currentLine;
                }
            } catch (FileNotFoundException fnfe){
                Log.d(Const.LOGTAG_FILE_READ, "Conversation file not found");
            } catch (IOException ioe){
                Log.d(Const.LOGTAG_FILE_READ, "Error reading conversation file");
            }

            //TODO implement profile picture displaying

            cards.add(new ConversationCard(username, lastLine, Const.URL_DEFAULT_PROFILE_PIC));
        }

        cardAdapter = new ConvoCardAdapter(GlobalState.getAppContext(), R.layout.conversation_card, cards);
        cardListView = view.findViewById(R.id.conversationListScreen_conversations);
        cardListView.setAdapter(cardAdapter);
        cardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // switch to conversation screen, passing username and profile picture of user
                screenSwitch = new Intent(getActivity(), ConversationScreen.class);
                screenSwitch.putExtra(Const.INTENT_PROFILE_VIEW_USERNAME, cards.get(position).getUsername());
                screenSwitch.putExtra(Const.INTENT_PROFILE_PIC, cards.get(position).getImageURL());
                startActivity(screenSwitch);
            }
        });

        return view;
    }
}
