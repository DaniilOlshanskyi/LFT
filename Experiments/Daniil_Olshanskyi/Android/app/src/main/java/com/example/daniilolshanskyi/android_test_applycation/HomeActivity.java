package com.example.daniilolshanskyi.android_test_applycation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        b1 = (Button) findViewById(R.id.buttonBack);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        Button SignInButton = (Button) findViewById(R.id.buttonSignIn);

        SignInButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        EditText email = (EditText) findViewById(R.id.userEmail);
                        Toast.makeText(getApplicationContext(), "You have entered:"+ email.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
