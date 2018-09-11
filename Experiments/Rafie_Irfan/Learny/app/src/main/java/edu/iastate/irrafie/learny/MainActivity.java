package edu.iastate.irrafie.learny;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private Button send_button;


    ArrayList<String> userInput = new ArrayList<String>();
    //this causes crashes
    //not due to null textbox


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Test


    }

    public void send_buttonClicked(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText emailText = (EditText) findViewById(R.id.emailText);
        EditText passText = (EditText) findViewById(R.id.passText);
        userInput.add(emailText.getText().toString());
        userInput.add(passText.getText().toString());
        String userMess = userInput.get(0);
        String passMess = userInput.get(1);
        intent.putExtra("user_text", userMess);
        intent.putExtra("pass_text", passMess);
        startActivity(intent);
    }

    public String getArrayList(int n){
        return userInput.get(n);
    }


}
