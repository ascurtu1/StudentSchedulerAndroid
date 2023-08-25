package com.example.studentscheduler.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.studentscheduler.Database.Repository;
import com.example.studentscheduler.Entity.Courses;
import com.example.studentscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class DetailedTerms extends AppCompatActivity {
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_terms);
    }

    //use recyclerview to display the list of associated courses
   /* RecyclerView recyclerview = findViewById(R.id.associatedCourses);
    CoursesAdapter coursesAdapter = new CoursesAdapter(this);
        recyclerview.setAdapter(coursesAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    List<Courses> filteredCourses = new ArrayList<>();
        for (Courses c: repository.getAllCourses()) {
        if(c.getTermID()==TermID)filteredCourses.add(c);
    }
        coursesAdapter.setCourses(filteredCourses);
}*/

}