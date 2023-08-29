package com.example.studentscheduler.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.studentscheduler.Database.Repository;
import com.example.studentscheduler.Entity.Courses;
import com.example.studentscheduler.Entity.Terms;
import com.example.studentscheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class CoursesList extends AppCompatActivity {

    private Repository repository;

    /**
     * Method to allow the display of the course data into the recycler view.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        RecyclerView recyclerView = findViewById(R.id.RV2);
        Repository repository = new Repository(getApplication());
        List<Courses> courses = repository.getAllCourses();
        CoursesAdapter adapter = new CoursesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);

        /** Creates a floating action button that allows for navigation to the next screen. */
        FloatingActionButton floatingActionButton2 = findViewById(R.id.fab2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursesList.this, AssessmentsList.class);
                startActivity(intent);
            }
        });

        /** Creates a button that brings the user to the Detailed Courses page. */
        Button addTerms = findViewById(R.id.addCourses);

        addTerms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CoursesList.this, DetailedCourses.class);
                startActivity(intent);
            }
        });
    }


        /**
         * Method to create a menu.
         */
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_course_list, menu);
            return true;

        }


        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                this.finish();
                return true;
            }
            if (item.getItemId() == R.id.AddCourses) {
                Intent detailedTerms = new Intent(CoursesList.this, DetailedCourses.class);
                startActivity(detailedTerms);
                return true;

            }

            if (item.getItemId() == R.id.RefreshTerms) {
                Repository repository = new Repository(getApplication());
                List<Terms> updatedTerms = repository.getAllTerms();
                RecyclerView recyclerView2 = findViewById(R.id.RV2);
                TermsAdapter termAdapter = new TermsAdapter(this);
                recyclerView2.setAdapter(termAdapter);
                recyclerView2.setLayoutManager(new LinearLayoutManager(this));
                termAdapter.setTerms(updatedTerms);
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }
