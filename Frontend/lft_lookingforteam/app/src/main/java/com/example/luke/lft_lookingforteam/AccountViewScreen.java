package com.example.luke.lft_lookingforteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.luke.lft_lookingforteam.net_utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountViewScreen extends AppCompatActivity {

    Button editButton;
    TextView username, availability;
    ImageView profilePic;
    JSONObject userProfile = null;

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
        JsonObjectRequest testrequest = new JsonObjectRequest(Request.Method.GET, Const.TEST_URL_GET_PROFILE_REQUEST, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Request received", Toast.LENGTH_LONG).show();
                        userProfile = response;


                        try{
                            // display username
                            username.setText(userProfile.getString(Const.PROFILE_USERNAME_KEY));

                            // display availability
                            availability.setText(userProfile.getString(Const.PROFILE_PERIOD_KEY));
                        } catch (JSONException jse){
                            Log.d("Prof_Info_Fill", jse.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        // TODO de-spaghettify
        // make request
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(testrequest);
        Toast.makeText(getApplicationContext(), "GET request made", Toast.LENGTH_LONG).show();
    }
}
