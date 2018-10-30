package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    Button editButton, logoutButton;
    TextView username, availability;
    ImageView profilePic;
    JSONObject userProfile = null;  // holds user profile obtained from server

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_view_screen);

        // reference layout objects
        editButton = findViewById(R.id.myProfileViewScreen_editProfileButton);
        logoutButton = findViewById(R.id.myProfileViewScreen_logoutButton);
        username = findViewById(R.id.accountView_username);
        availability = findViewById(R.id.accountView_availability);
        profilePic = findViewById(R.id.accountView_profilePic);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // reset SharedPrefs to effectively log user out
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear().commit();

                // go to login screen
                Intent i = new Intent(MyProfileViewScreen.this, LoginScreen.class);
                startActivity(i);
            }
        });

//        // create a GET request for user profile
//        JsonObjectRequest testrequest = new JsonObjectRequest(Request.Method.GET, Const.URL_GET_PROFILE_BY_USERNAME + profileUsername, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // when server sends back profile object
//                        userProfile = response;
//                        try {
//                            username.setText(userProfile.getString(Const.PROFILE_USERNAME_KEY));    // display username
//                            availability.setText(userProfile.getString(Const.PROFILE_PERIOD_KEY));  // display availability
//                            //TODO display more stuff
//                        } catch (JSONException jse) {
//                            // if an error occurs with the JSON, log it
//                            Log.d("Prof_Info_Fill", jse.toString());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // if a volley error occurs, log it
//                        Log.d("Prof_GET_Req", error.toString());
//                    }
//                });
//
//        // make GET request
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        queue.add(testrequest);
    }
}