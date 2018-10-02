package com.example.luke.lft_lookingforteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class AccountViewScreen extends AppCompatActivity {

    Button editButton;
    TextView username, availability;
    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_view_screen);

        // reference layout objects
        editButton = findViewById(R.id.accountView_EditProfileButton);
        username = findViewById(R.id.accountView_username);
        availability = findViewById(R.id.accountView_availability);
        profilePic = findViewById(R.id.accountView_profilePic);

        // TODO get profile from server and fill the layout

        //JsonObjectRequest testrequest = new JsonObjectRequest(Request.Method.GET, )

    }
}
