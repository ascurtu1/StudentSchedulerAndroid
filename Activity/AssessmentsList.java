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
import com.example.studentscheduler.Entity.Assessments;
import com.example.studentscheduler.Entity.Terms;
import com.example.studentscheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AssessmentsList extends AppCompatActivity {
    private Repository repository;

    /**
     * Method to allow the display of the assessments data into the recycler view.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        RecyclerView recyclerView = findViewById(R.id.RV3);
        Repository repository = new Repository(getApplication());
        List<Assessments> assessments = repository.getAllAssessments();
        AssessmentsAdapter adapter = new AssessmentsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);

        /** Creates a button that brings the user to the Detailed Assessments page. */
        Button addTerms = findViewById(R.id.addAssessments);

        addTerms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentsList.this, DetailedAssessments.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Method to create a menu.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_list, menu);
        return true;

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.addAssessments) {
            Intent detailedTerms = new Intent(AssessmentsList.this, DetailedAssessments.class);
            startActivity(detailedTerms);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
