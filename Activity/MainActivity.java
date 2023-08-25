package com.example.studentscheduler.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.studentscheduler.Database.Repository;
import com.example.studentscheduler.Entity.Assessments;
import com.example.studentscheduler.Entity.Courses;
import com.example.studentscheduler.Entity.Terms;
import com.example.studentscheduler.R;

/** This is the main class that launches the app and provides a Home-screen. */
public class MainActivity extends AppCompatActivity {

    /**
     * The methods below allow the user to click a button and brings them to that screen.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button TermsBtn = findViewById(R.id.TermsBtn);
        TermsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TermsList.class);
                startActivity(intent);
            }
        });


        Button CoursesBtn = findViewById(R.id.CoursesBtn);
        CoursesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CoursesList.class);
                startActivity(intent);
            }
        });


        Button AssessmentsBtn = findViewById(R.id.AssessmentsBtn);
        AssessmentsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AssessmentsList.class);
                startActivity(intent);
            }
        });

        Repository repository = new Repository(getApplication());
        Terms term = new Terms(1, "term1", "11/1/2023", "5/1/2024");
        Terms term2 = new Terms(2, "term2", "12/1/2023", "7/1/2024");


        Courses course = new Courses(1, 1, "course1", "12/1/2023", "7/1/2024", "Plan to Take", "John Smith", "8156349872", "myInstructor@yahoo.com", "This instructor is the best!");
        Courses course2 = new Courses(2, 2, "course2", "2/1/2024", "9/1/2024", "Dropped", "Sarah Socool", "8153324527", "coolcat@yahoo.com", "This class is difficult but rewarding!");

        Assessments assessment = new Assessments(1, 1, "Final Exam", "Performance Assessment", "11/1/2023", "5/1/2024");
        Assessments assessment2 = new Assessments(2, 2, "Midterm", "Objective Assessment", "12/1/2023", "6/1/2024");

        repository.insert(term);
        repository.insert(term2);
        repository.insert(course);
        repository.insert(course2);
        repository.insert(assessment);
        repository.insert(assessment2);

    }
}
