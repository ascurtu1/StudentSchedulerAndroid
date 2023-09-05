package com.example.studentscheduler.Activity;

import static com.example.studentscheduler.R.*;

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
import com.example.studentscheduler.Entity.Terms;
import com.example.studentscheduler.R;
import com.example.studentscheduler.R.id;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermsList extends AppCompatActivity {
    private Repository repository;

    /**
     * Method to allow the display of the data into the recycler view.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_term_list);
        RecyclerView recyclerView = findViewById(id.RV1);
        Repository repository = new Repository(getApplication());
        List<Terms> terms = repository.getAllTerms();
        TermsAdapter adapter = new TermsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);

        /** Creates a button that brings the user to the Detailed Terms page. */
        Button addTerms = findViewById(id.addTerms);

        addTerms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TermsList.this, DetailedTerms.class);
                startActivity(intent);
            }
        });

        /** Creates a floating action button that allows for navigation to the next screen. */
        FloatingActionButton fab = findViewById(id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsList.this, CoursesList.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Method to create a menu.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == id.homePage) {
            Intent intent = new Intent(TermsList.this, MainActivity.class);
            startActivity(intent);

            return true;
        }
        if (item.getItemId() == id.AddTerms) {
            Intent intent = new Intent(TermsList.this, DetailedTerms.class);
            startActivity(intent);

            return true;
        }

        if (item.getItemId() == id.RefreshTerms) {
            Repository repository = new Repository(getApplication());
            List<Terms> updatedTerms = repository.getAllTerms();
            RecyclerView recyclerView2 = findViewById(R.id.RV1);
            TermsAdapter termAdapter = new TermsAdapter(this);
            recyclerView2.setAdapter(termAdapter);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));
            termAdapter.setTerms(updatedTerms);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

