package com.example.daniilolshanskyi.android_test_applycation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "my_log_message";

    Button b1, b2;

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "OnStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        b1 = (Button) findViewById(R.id.buttonMessage);
        b2 = (Button) findViewById(R.id.buttonNext);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Here is your toast text!", Toast.LENGTH_LONG).show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}
