package com.example.daniilolshanskyi.android_test_applycation;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;


public class SwipeActivity extends Activity {

    private static final String TAG = "my_log_message";

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "SwipeOnStart ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
    }

}
