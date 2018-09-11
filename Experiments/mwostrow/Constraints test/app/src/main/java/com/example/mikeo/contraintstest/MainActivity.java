package com.example.mikeo.contraintstest;

import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ConstraintSet setOne = new ConstraintSet;
    ConstraintSet setTwo = new ConstraintSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setTwo.clone(this, R.layout.layout2);
        setOne.clone(binding.include.constraint);
    }

    public void changeContstraints(View view){
        TransitionManager.beginDelayedTransition(binding.include.constraint);
        setTwo.applyTo(binding.include.constraint);
    }
}
