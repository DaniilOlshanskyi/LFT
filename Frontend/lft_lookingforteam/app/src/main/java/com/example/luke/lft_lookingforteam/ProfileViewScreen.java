package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import com.bumptech.glide.Glide;

/**
 * Manages other user profile view interface and operations
 */
public class ProfileViewScreen extends AppCompatActivity {

    Button report, backButton;
    TextView usernameText;
    String profileUsername, profileImageURL;
    int profileId;
    JSONObject userProfile;
    RequestQueue queue;
    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view_screen);

        report = findViewById(R.id.profileViewScreen_reportButton);
        backButton = findViewById(R.id.profileViewScreen_backButton);
        usernameText = findViewById(R.id.profileViewScreen_username);
        profilePic = findViewById(R.id.profileViewScreen_profilePic);

        // get username from intent
        Intent i = getIntent();
        profileUsername = i.getStringExtra(Const.INTENT_PROFILE_VIEW_USERNAME);
        profileImageURL = i.getStringExtra(Const.PROFILE_PHOTO_KEY);

        usernameText.setText(profileUsername);

        JsonObjectRequest reportRequest = new JsonObjectRequest(Request.Method.GET, Const.URL_GET_PROFILE_BY_USERNAME + profileUsername,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        userProfile = response;
                        try {
                            profileId = userProfile.getInt(Const.PROFILE_ID_KEY);
//                            profileImageURL = userProfile.getString(Const.PROFILE_PHOTO_KEY);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //when server sends back profile object
                        //TODO fill in games and profile picture
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Prof_GET_Req", error.toString());

                    }
                });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(reportRequest);

        Context context = ProfileViewScreen.this;
        Glide.with(context).load(Uri.parse(Const.URL_PROFILE_PICTURES + profileImageURL)).into(profilePic);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(ProfileViewScreen.this, ReportCreateScreen.class);
//                j.putExtra(Const.INTENT_PROFILE_VIEW_USERNAME, profileId);
                startActivity(j);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
