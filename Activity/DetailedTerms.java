package com.example.studentscheduler.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentscheduler.Database.Repository;
import com.example.studentscheduler.Entity.Courses;
import com.example.studentscheduler.Entity.Terms;
import com.example.studentscheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedTerms extends AppCompatActivity {
    Repository repository;
    Button saveBtn;
    EditText editTermTitle;
    EditText editTermStart;
    EditText editTermEnd;

    String title;

    String start;
    String end;

    DatePickerDialog.OnDateSetListener termStart;
    DatePickerDialog.OnDateSetListener termEnd;
    int termID;
    final Calendar calendarStartTerm = Calendar.getInstance();
    final Calendar calendarEndTerm = Calendar.getInstance();
    Terms selectedTerm;


    /**
     * This creates the display for the detailed terms screen and populates it with information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_terms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editTermTitle = findViewById(R.id.termTitleEdit);
        editTermStart = findViewById(R.id.termStartEdit);
        editTermEnd = findViewById(R.id.termEndEdit);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat Startsdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat Endsdf = new SimpleDateFormat(myFormat, Locale.US);
        editTermStart.setText(Startsdf.format(new Date()));
        editTermEnd.setText(Endsdf.format(new Date()));


        title = getIntent().getStringExtra("Title");
        start = getIntent().getStringExtra("Start");
        end = getIntent().getStringExtra("End");
        termID = getIntent().getIntExtra("ID", -1);

        editTermTitle.setText(title);
        editTermStart.setText(start);
        editTermEnd.setText(end);

        repository = new Repository(getApplication());

    /**
     * The below methods allow for the creation of a calendar to allow the user to insert term start and end dates.
     */
        editTermStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateProvided = editTermStart.getText().toString();
                if (dateProvided.equals("")) dateProvided = "09/01/23";
                try {
                    calendarStartTerm.setTime(Startsdf.parse(dateProvided));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(DetailedTerms.this, termStart, calendarStartTerm.get(Calendar.YEAR),
                        calendarStartTerm.get(Calendar.MONTH), calendarStartTerm.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editTermEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateEnteredEnd = editTermEnd.getText().toString();
                if (dateEnteredEnd.equals("")) dateEnteredEnd = "09/01/23";
                try {
                    calendarEndTerm.setTime(Endsdf.parse(dateEnteredEnd));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(DetailedTerms.this, termEnd, calendarEndTerm.get(Calendar.YEAR),
                        calendarEndTerm.get(Calendar.MONTH), calendarEndTerm.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        termStart = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarStartTerm.set(Calendar.YEAR, year);
                calendarStartTerm.set(Calendar.MONTH, month);
                calendarStartTerm.set(Calendar.DAY_OF_MONTH, day);
                editTermStart.setText(Startsdf.format(calendarStartTerm.getTime()));
                updateLabelStart();
            }

            private void updateLabelStart() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editTermStart.setText(sdf.format(calendarStartTerm.getTime()));
            }
        };


        termEnd = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarEndTerm.set(Calendar.YEAR, year);
                calendarEndTerm.set(Calendar.MONTH, month);
                calendarEndTerm.set(Calendar.DAY_OF_MONTH, day);
                updateLabelEnd();
            }

            private void updateLabelEnd() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editTermEnd.setText(sdf.format(calendarEndTerm.getTime()));
            }
        };


        /**
         * Method allows the user to click the save button and either save a new term or update a current term.
         */
        saveBtn = findViewById(R.id.saveTermBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleProvided = editTermTitle.getText().toString();
                String dateProvided = editTermStart.getText().toString();
                String dateEnteredEnd = editTermEnd.getText().toString();

                Repository repository = new Repository(getApplication());
                if (termID != -1) {
                    Terms term = new Terms(termID, titleProvided, dateProvided, dateEnteredEnd);
                    repository.update(term);
                } else {
                    Terms term = new Terms(0, titleProvided, dateProvided, dateEnteredEnd);
                    repository.insert(term);
                }

                Intent intent = new Intent(DetailedTerms.this, TermsList.class);
                startActivity(intent);
            }
        });


        /**
         * Recycler view displays the list of associated courses.
         */
        RecyclerView recyclerview = findViewById(R.id.associatedCourses);
        repository = new Repository(getApplication());
        final CoursesAdapter coursesAdapter = new CoursesAdapter(this);
        recyclerview.setAdapter(coursesAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        List<Courses> filteredCourses = new ArrayList<>();
        for (Courses c : repository.getAllCourses()) {
            if (c.getTermID() == termID) {
                filteredCourses.add(c);
            }
        }
        coursesAdapter.setCourses(filteredCourses);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_detailed, menu);
        return true;
    }

    /**
     * Options menu which allows user to delete a term and issues appropriate warnings.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        } else {
            int count = 0;
            for (Courses course : repository.getAllCourses()) {
                if (course.getTermID() == termID) {
                    ++count;
                }
            }
            if (count == 0) {
                Terms term = new Terms(termID, title, start, end);
                Toast.makeText(this, "The term has been deleted.", Toast.LENGTH_SHORT).show();
                repository.delete(term);
                Intent intent = new Intent(DetailedTerms.this, TermsList.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Unable to delete term due to associated course.", Toast.LENGTH_SHORT).show();
            }
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}






