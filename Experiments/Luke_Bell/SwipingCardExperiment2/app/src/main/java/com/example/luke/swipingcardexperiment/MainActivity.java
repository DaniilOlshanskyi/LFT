package com.example.luke.swipingcardexperiment;

import android.os.Bundle;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private SwipeFlingAdapterView flingContainer;

    private ArrayList<SwipeCard> cards;
    private SwipeCardAdapter swipeCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cards = new ArrayList<>();
        cards.add(new SwipeCard("EpicGamer42", "24/7", "https://i.imgur.com/sujJc7j.jpg", new ArrayList<String>(), new ArrayList<String>()));
        cards.add(new SwipeCard("XxRENEGADExX", "when mom's not home", "https://i.imgur.com/HHc6NA8.jpg", new ArrayList<String>(), new ArrayList<String>()));
        cards.add(new SwipeCard("GenericWeeb", "when not watching anime", "https://i.imgur.com/GMNcgFm.png", new ArrayList<String>(), new ArrayList<String>()));
        cards.add(new SwipeCard("Jim", "weeknights after 5", "https://i.imgur.com/0N7eOV1.png", new ArrayList<String>(), new ArrayList<String>()));
        cards.add(new SwipeCard("Yuri", "idk", "https://i.imgur.com/l9QBJrS.png", new ArrayList<String>(), new ArrayList<String>()));
        cards.add(new SwipeCard("ThugGandalf", "weekends", "https://i.imgur.com/3u2wK0J.png", new ArrayList<String>(), new ArrayList<String>()));

        swipeCardAdapter = new SwipeCardAdapter(this, R.layout.swipe_card, cards);
        flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(swipeCardAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                cards.remove(0);
                swipeCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(MainActivity.this, "pass", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(MainActivity.this, "connect", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "go to user's profile", Toast.LENGTH_SHORT).show();
            }
        });

        Button leftBtn = findViewById(R.id.left);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left();
            }
        });

        Button rightBtn = findViewById(R.id.right);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right();
            }
        });

    }

    public void right() {
        flingContainer.getTopCardListener().selectRight();
    }

    public void left() {
        flingContainer.getTopCardListener().selectLeft();
    }
}