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


import com.example.studentscheduler.Database.Repository;
import com.example.studentscheduler.Entity.Terms;
import com.example.studentscheduler.R;
import com.example.studentscheduler.R.id;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermsList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_term_list);
        RecyclerView recyclerView =findViewById(id.RV1);
        Repository repository = new Repository(getApplication());
        List<Terms> terms = repository.getAllTerms();
        TermsAdapter adapter = new TermsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);

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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;

    }

    /**
     * Method to create a menu for navigation.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == id.AddTerms) {

        }
        return false;
    }
}
