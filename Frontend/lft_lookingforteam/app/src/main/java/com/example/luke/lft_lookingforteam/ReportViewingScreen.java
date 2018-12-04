package com.example.luke.lft_lookingforteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Manages the administrative report-viewing interface and operations
 */
public class ReportViewingScreen extends AppCompatActivity {

    Button cancelButton2, suspendButton, banButton, viewButton;
    TextView messageText, messageTextSend;
    int reportId, profId;
    String messageSent, chatLog, profUsername;
    JSONObject report, badProfile;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_viewing_screen);
        queue = Volley.newRequestQueue(getApplicationContext());

        profUsername = getIntent().getStringExtra(Const.INTENT_PROFILE_VIEW_USERNAME);
        reportId = getIntent().getIntExtra("reportId", 1);

        JsonObjectRequest reportRequest = new JsonObjectRequest(Request.Method.GET, Const.URL_GET_REPORT_BY_ID + reportId,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //when server sends back profile object
                        report = response;
                        try {
                            messageSent = report.getString(Const.REPORT_MESSAGE); //Gives profId
                            chatLog = report.getString(Const.REPORT_CHATLOG); //Gives Chatlog
                        } catch (JSONException jse) {
                            // if an error occurs with the JSON, log it
                            Log.d("Prof_Info_Fill", jse.toString());
                        }
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

        cancelButton2 = findViewById(R.id.cancelButton2);
        suspendButton = findViewById(R.id.suspendButton);
        banButton = findViewById(R.id.banButton);
        viewButton = findViewById(R.id.viewButton);
        messageText = findViewById(R.id.messageText);
        messageTextSend = findViewById(R.id.messageTextSend);

        messageText.setText("He's a Jerk");

        cancelButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        suspendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create punishment Post
                finish();
            }
        });

        banButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create punishment Post
                finish();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(chatLog.length() == 0){
                    Intent i = new Intent(ReportViewingScreen.this, ProfileViewScreen.class);
                    i.putExtra(Const.INTENT_PROFILE_VIEW_USERNAME, profUsername);
                    i.putExtra(Const.PROFILE_PHOTO_KEY, "placeholder.JPG");
                    startActivity(i);
//                }else{
//                    //TODO show chatlog somehow
//                }
            }
        });

    }
}
