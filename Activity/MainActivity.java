package com.example.studentscheduler.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.studentscheduler.R;

/** This is the main class that launches the app and provides a Home-screen. */
public class MainActivity extends AppCompatActivity {

    /** The methods below allow the user to click a button and brings them to that screen. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    Button TermsBtn = findViewById(R.id.TermsBtn);
    TermsBtn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, TermList.class);
            startActivity(intent);
        }
    });


        Button CoursesBtn = findViewById(R.id.CoursesBtn);
        CoursesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CourseList.class);
                startActivity(intent);
            }
        });


        Button AssessmentsBtn = findViewById(R.id.AssessmentsBtn);
        AssessmentsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AssessmentList.class);
                startActivity(intent);
            }
        });
    }

}