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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;

/**
 * Fragment used to manage the swiping/matching interface and operations
 */
public class SwipingScreenFragment extends Fragment {

    private ArrayList<SwipingCard> cards;
    private SwipeCardAdapter cardAdapter;
    private SwipeFlingAdapterView flingAdapterView;

    private Intent screenSwitch;
    private WebSocketClient websocket;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_screen, container, false);

        // get app state and websocket
        final GlobalState appState = (GlobalState) GlobalState.getAppContext();
        websocket = GlobalState.getWebsocket();

        // get first two swipe candidates
        cards = new ArrayList<>();
        SwipingCard temp;
        temp = appState.getSwipeCandidate();

        if (temp == null){
            temp = new SwipingCard("No users available", "", Const.URL_DEFAULT_PROFILE_PIC, new ArrayList<String>(), new ArrayList<String>());
            cards.add(temp);
        } else {
            cards.add(temp);
            cards.add(appState.getSwipeCandidate());
        }

        // instantiate buttons and set click listeners
        Button passBtn = view.findViewById(R.id.swipeScreen_passButton);
        Button connectBtn = view.findViewById(R.id.swipeScreen_connectButton);
        Button profileViewBtn = view.findViewById(R.id.swipeScreen_viewProfileButton);
        ImageButton swipeSettingsBtn = view.findViewById(R.id.swipeScreen_swipeSettingsButton);

        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // simulate swipe left
                left();
            }
        });

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // simulate swipe right
                right();
            }
        });
//cards.get(0).getUsername()
        profileViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // switch to profile view screen
                screenSwitch = new Intent(getActivity(), ProfileViewScreen.class);
                screenSwitch.putExtra(Const.INTENT_PROFILE_VIEW_USERNAME, "testusername1");
                startActivity(screenSwitch);
            }
        });

        swipeSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO implement swipe settings
                Toast.makeText(GlobalState.getAppContext(), "Swipe filters not yet implemented", Toast.LENGTH_SHORT).show();
            }
        });

        // set up fling adapter
        cardAdapter = new SwipeCardAdapter(GlobalState.getAppContext(), R.layout.swipe_card, cards);
        flingAdapterView = view.findViewById(R.id.swipeScreen_swipeFlingAdapter);
        flingAdapterView.setAdapter(cardAdapter);
        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                cards.remove(0);
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                //TODO implement "pass"
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                // right means "connect" so send match request to server, and log it
                String username = ((SwipingCard) dataObject).getUsername();
                Log.d(Const.LOGTAG_WEBSOCKET, "Sending match request for " + username);
                websocket.send(Const.WEBSOCKET_MATCHING_TAG + username);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // get another card and notify the adapter
                cards.add(appState.getSwipeCandidate());
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });

        // when clicked, cards will take user to view candidate's profile
        flingAdapterView.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                // switch to profile view screen
                screenSwitch = new Intent(getActivity(), ProfileViewScreen.class);
                screenSwitch.putExtra(Const.INTENT_PROFILE_VIEW_USERNAME, cards.get(0).getUsername());
                startActivity(screenSwitch);
            }
        });

        return view;
    }

    /**
     * Helper method that emulates a swipe right
     */
    public void right() {
        flingAdapterView.getTopCardListener().selectRight();
    }

    /**
     * Helper method that emulates a swipe left
     */
    public void left() {
        flingAdapterView.getTopCardListener().selectLeft();
    }
}
