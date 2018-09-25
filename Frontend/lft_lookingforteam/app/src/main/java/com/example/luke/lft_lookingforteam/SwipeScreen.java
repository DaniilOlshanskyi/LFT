package com.example.luke.lft_lookingforteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.luke.lft_lookingforteam.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class SwipeScreen extends AppCompatActivity {

    TextView username = (TextView) findViewById(R.id.swipeUserName);

    String testURL = "http://proj309-ad-08.misc.iastate.edu:8080/all/1";
    private String TAG = "user profile retrieval";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_screen);

        JsonObjectRequest profileReq = new JsonObjectRequest(Request.Method.GET,
                testURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            username.setText(response.getString("prof_username"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(profileReq);
    }
}
