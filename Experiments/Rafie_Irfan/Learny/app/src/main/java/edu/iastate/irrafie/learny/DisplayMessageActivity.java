package edu.iastate.irrafie.learny;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent=  getIntent();
        String here1 = intent.getExtras().getString("user_text");
        String here2 = intent.getExtras().getString("pass_text");
        TextView text1 = (TextView)findViewById(R.id.text1);
        TextView text2 = (TextView)findViewById(R.id.text2);


        text1.setText(here1);
        text2.setText(here2);

    }
}
