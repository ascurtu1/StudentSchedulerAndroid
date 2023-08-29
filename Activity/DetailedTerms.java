package com.example.studentscheduler.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
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
    Button termSave;
    EditText editTermTitle;
    EditText editTermStart;
    EditText editTermEnd;

    DatePickerDialog.OnDateSetListener termStart;
    DatePickerDialog.OnDateSetListener termEnd;
    int termID;
    final Calendar calendarStartTerm = Calendar.getInstance();
    final Calendar calendarEndTerm = Calendar.getInstance();
    Terms selectedTerm;
    int numberOfCourses;


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
        editTermStart.setText(Endsdf.format(new Date()));


        editTermTitle.setText(getIntent().getStringExtra("Term Title"));
        editTermStart.setText(getIntent().getStringExtra("Term Start"));
        editTermStart.setText(getIntent().getStringExtra("Term End"));
        termID = getIntent().getIntExtra("Term ID", -1);

        repository = new Repository(getApplication());


        editTermStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateProvided = editTermStart.getText().toString();
                if (dateProvided.equals("")) {
                    dateProvided = "08/10/23";
                    try {
                        calendarStartTerm.setTime(Startsdf.parse(dateProvided));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    new DatePickerDialog(DetailedTerms.this, termStart, calendarStartTerm.get(Calendar.YEAR),
                            calendarStartTerm.get(Calendar.MONTH), calendarStartTerm.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        editTermEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateEnteredEnd = editTermEnd.getText().toString();
                if (dateEnteredEnd.equals("")) dateEnteredEnd = "08/10/23";
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


        termSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleProvided = editTermTitle.getText().toString();
                String dateProvided = editTermStart.getText().toString();
                String dateEnteredEnd = editTermEnd.getText().toString();

                Repository repository = new Repository(getApplication());
                if (termID != -1) {
                    Terms term = new Terms(termID, titleProvided, dateProvided, dateEnteredEnd);
                    repository.update(term);
                } else {
                    Terms term = new Terms(termID, titleProvided, dateProvided, dateEnteredEnd);
                    repository.insert(term);
                }

                finish();
            }
        });


        /**
         * Recycler view displays the list of associated courses.
         */
        RecyclerView recyclerview = findViewById(R.id.associatedCourses);
        repository = new Repository(getApplication());
        CoursesAdapter coursesAdapter = new CoursesAdapter(this);
        recyclerview.setAdapter(coursesAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        List<Courses> filteredCourses = new ArrayList<>();
        for (Courses c : repository.getAllCourses()) {
            if (c.getTermID() == termID) {
                filteredCourses.add(c);
            }
            coursesAdapter.setCourses(filteredCourses);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_detailed, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.deleteTerm) {
            for (Terms t : repository.getAllTerms()) {
                if (t.getTermID() == termID) {
                    selectedTerm = t;
                    repository.delete(selectedTerm);
                    Toast.makeText(DetailedTerms.this, "The term has been deleted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailedTerms.this, "The term cannot be deleted as it has a course attached to it.", Toast.LENGTH_SHORT).show();

                }
            }
            finish();

        } return super.onOptionsItemSelected(item);
    }
}





