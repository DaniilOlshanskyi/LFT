package com.example.mikeo.secondexperiment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    EditText name, email;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (EditText) findViewById(R.id.nameType);
        email = (EditText) findViewById(R.id.emailType);

        submit = (Button) findViewById(R.id.bSubmit);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = name.getText().toString();
//                TextView nameView = findViewById(R.id.nameView);
//                nameView.setText(nameString);
//
                String emailString = name.getText().toString();
//                TextView emailView = findViewById(R.id.emailView);
//                emailView.setText(emailString);

                Intent j = new Intent(Main2Activity.this, Main3Activity.class);

                j.putExtra("nameMsg", nameString);
                j.putExtra("emailMsg", emailString);
                startActivity(j);
            }
        });
    }

}
