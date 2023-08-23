package com.example.studentscheduler.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentscheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CoursesList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        /** Creates a floating action button that allows for navigation to the next screen. */
        FloatingActionButton floatingActionButton2 = findViewById(R.id.fab2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursesList.this, AssessmentsList.class);
                startActivity(intent);
            }
        });



    }
}