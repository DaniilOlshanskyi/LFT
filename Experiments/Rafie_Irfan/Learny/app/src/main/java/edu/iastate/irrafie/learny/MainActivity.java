package edu.iastate.irrafie.learny;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private Button send_button;
    private EditText emailText;
    private EditText passText;

    //this causes crashes
    //not due to null textbox

    private View.OnClickListener sendButtonOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            send_buttonClicked();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Test


        send_button.setOnClickListener(sendButtonOnClickListener);
    }

    private void send_buttonClicked(){
        String email = emailText.toString();
        String pass = passText.toString();
        emailText.setText("Test");
        passText.setText("Test");
    }



}
