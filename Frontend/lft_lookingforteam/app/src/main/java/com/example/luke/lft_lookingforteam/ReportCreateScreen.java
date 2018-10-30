package com.example.luke.lft_lookingforteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.luke.lft_lookingforteam.net_utils.Const;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportCreateScreen extends AppCompatActivity {


    private RequestQueue reqQueue;
    Button submitButton, cancelButton;
    CheckBox profileCheck, messageCheck;
    EditText messageEdit;
    String message, chatlog, profile;
    Boolean chat, prof;
    Date date = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_create_screen2);

        submitButton = findViewById(R.id.submitButton);
        cancelButton = findViewById(R.id.cancelButton);
        profileCheck = findViewById(R.id.profileCheck);
        messageCheck = findViewById(R.id.messageCheck);
        messageEdit = findViewById(R.id.messageEdit);


        InputFilter[] reFilterArray = new InputFilter[1];
        reFilterArray[0] = new InputFilter.LengthFilter(Const.REPORT_CHARACTER_LIMIT);
        messageEdit.setFilters(reFilterArray);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message = messageEdit.getText().toString();
                chat = messageCheck.isChecked();
                prof = profileCheck.isChecked();

                //TODO Create link between profile and report

                JSONObject newReport = new JSONObject();
                try{
                    if(chat){
                        newReport.put(Const.REPORT_CHATLOG, chatlog);
                    }else{
                        newReport.put(Const.REPORT_CHATLOG, "");
                    }
                    newReport.put(Const.REPORT_RESOLVE_FLAG, false);
                    newReport.put(Const.REPORT_RESOLVE_DATE, date.getTime());
                    newReport.put(Const.REPORT_MESSAGE, message);

                } catch (JSONException jse) {
                    Log.e("Creating JSON", jse.toString());
                }
                JsonObjectRequest postReq = new JsonObjectRequest(Request.Method.POST, Const.URL_POST_PROFILE, newReport,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // notify user that their account has been created :]
                                Toast.makeText(getApplicationContext(), "Account created :]", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Report_POST_Req", error.toString());
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Content-Type", "application/json");
                        return params;
                    }
                };

                // make POST request
                reqQueue = Volley.newRequestQueue(getApplicationContext());
                reqQueue.add(postReq);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
