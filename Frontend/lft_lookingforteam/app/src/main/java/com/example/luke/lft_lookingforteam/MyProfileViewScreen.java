package com.example.luke.lft_lookingforteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.luke.lft_lookingforteam.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfileViewScreen extends AppCompatActivity {

    Button editButton;  // allows user to edit their profile content
    TextView username, availability;
    ImageView profilePic;
    JSONObject userProfile = null;  // holds user profile obtained from server

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_view_screen);

        // get username of logged-in profile (passed from previous activity)
        String profileUsername = getIntent().getStringExtra("PROFILE_USERNAME");

        // reference layout objects
        editButton = findViewById(R.id.accountView_EditProfileButton);
        username = findViewById(R.id.accountView_username);
        availability = findViewById(R.id.accountView_availability);
        profilePic = findViewById(R.id.accountView_profilePic);

        // create a GET request for user profile
        JsonObjectRequest testrequest = new JsonObjectRequest(Request.Method.GET, Const.URL_GET_PROFILE_BY_USERNAME + profileUsername, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // when server sends back profile object
                        userProfile = response;
                        try{
                            // display username
                            username.setText(userProfile.getString(Const.PROFILE_USERNAME_KEY));

                            // display availability
                            availability.setText(userProfile.getString(Const.PROFILE_PERIOD_KEY));
                        } catch (JSONException jse){
                            // if an error occurs with the JSON, log it
                            Log.d("Prof_Info_Fill", jse.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if a volley error occurs, log it
                        Log.d("Prof_GET_Req", error.toString());
                    }
                });

        // make GET request
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(testrequest);
    }
}