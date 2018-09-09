package com.example.mikeo.hello_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button b1, b2;
    ImageView dog, cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.buttonYes);
        b1.bringToFront();
        b2 = (Button) findViewById(R.id.buttonNo);
        b2.bringToFront();

        dog = (ImageView) findViewById(R.id.imageDog);
        cat = (ImageView) findViewById(R.id.imageCat);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dog.setVisibility(View.INVISIBLE);
                cat.setVisibility(View.VISIBLE);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dog.setVisibility(View.INVISIBLE);
                cat.setVisibility(View.VISIBLE);
            }
        });
    }
}
