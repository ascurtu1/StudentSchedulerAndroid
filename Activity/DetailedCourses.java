package com.example.studentscheduler.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studentscheduler.R;

public class DetailedCourses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_courses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}