package com.example.studentscheduler.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studentscheduler.R;

public class DetailedAssessments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}