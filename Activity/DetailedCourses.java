package com.example.studentscheduler.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentscheduler.Database.Repository;
import com.example.studentscheduler.Entity.Courses;
import com.example.studentscheduler.Entity.Terms;
import com.example.studentscheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailedCourses extends AppCompatActivity {

    EditText courseTitleEdit;
    EditText courseStartEdit;
    EditText courseEndEdit;
    EditText instructorEdit;
    EditText instructorEmailEdit;
    EditText instructorPhoneEdit;
    EditText courseNoteEdit;
    Spinner courseStatusSpin;
    Spinner termSpin;
    String courseTitle;
    String courseStart;
    String courseEnd;
    String iName;
    String iEmail;
    String iPhone;
    String courseNote;
    String courseStatus;
    int courseID;
    int termID;
    Repository repository;
    final Calendar coursesCalendarStart = Calendar.getInstance();
    final Calendar coursesCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener courseStartDateCalendar;
    DatePickerDialog.OnDateSetListener courseEndDateCalendar;

    /** Populating the page with the information from the database. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_courses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Repository repository = new Repository(getApplication());

        String myFormat = "MM/dd/yy";
        SimpleDateFormat startDate = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat endDate = new SimpleDateFormat(myFormat, Locale.US);
        courseStartEdit.setText(startDate.format(new Date()));
        courseEndEdit.setText(endDate.format(new Date()));

        courseTitleEdit = findViewById(R.id.courseTitleEdit);
        courseStartEdit = findViewById(R.id.courseStartEdit);
        courseEndEdit = findViewById(R.id.courseEndEdit);
        instructorEdit = findViewById(R.id.instructorEdit);
        instructorEmailEdit = findViewById(R.id.instructorEmailEdit);
        instructorPhoneEdit = findViewById(R.id.instructorPhoneEdit);
        courseNoteEdit = findViewById(R.id.courseNoteEdit);
        courseStatusSpin = findViewById(R.id.courseStatusSpin); //spinner
        termSpin = findViewById(R.id.termSpin); //spinner

        courseTitle = getIntent().getStringExtra("Title");
        courseStart = getIntent().getStringExtra("Start");
        courseEnd = getIntent().getStringExtra("End");
        iName = getIntent().getStringExtra("Instructor");
        iEmail = getIntent().getStringExtra("Instructor Email");
        iPhone = getIntent().getStringExtra("Instructor Phone");
        courseNote = getIntent().getStringExtra("Notes");
        courseStatus = getIntent().getStringExtra("Status");
        courseID = getIntent().getIntExtra("Course ID", -1);
        termID = getIntent().getIntExtra("Term ID", -1);

        courseTitleEdit.setText(courseTitle);
        courseStartEdit.setText(courseStart);
        courseEndEdit.setText(courseEnd);
        instructorEdit.setText(iName);
        instructorEmailEdit.setText(iEmail);
        instructorPhoneEdit.setText(iPhone);
        courseNoteEdit.setText(courseNote);


        repository = new Repository(getApplication());


        /** Populating the spinner for course status. */


/**
 * The below methods allow for the creation of a calendar to allow the user to insert term start and end dates.
 */
        courseStartEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String dateProvided = courseStartEdit.getText().toString();
                if (dateProvided.equals("")) dateProvided = "09/01/23";
                try {
                    coursesCalendarStart.setTime(startDate.parse(dateProvided));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(DetailedCourses.this, courseStartDateCalendar, coursesCalendarStart.get(Calendar.YEAR),
                        coursesCalendarStart.get(Calendar.MONTH), coursesCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        courseEndEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateEnteredEnd = courseEndEdit.getText().toString();
                if (dateEnteredEnd.equals("")) dateEnteredEnd = "09/01/23";
                try {
                    coursesCalendarEnd.setTime(endDate.parse(dateEnteredEnd));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(DetailedCourses.this, courseEndDateCalendar, coursesCalendarEnd.get(Calendar.YEAR),
                        coursesCalendarEnd.get(Calendar.MONTH), coursesCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        courseStartDateCalendar = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                coursesCalendarStart.set(Calendar.YEAR, year);
                coursesCalendarStart.set(Calendar.MONTH, month);
                coursesCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                courseStartEdit.setText(startDate.format(coursesCalendarStart.getTime()));
                updateLabelStart();
            }

            private void updateLabelStart() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                courseStartEdit.setText(sdf.format(coursesCalendarStart.getTime()));
            }
        };


        courseEndDateCalendar = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                coursesCalendarEnd.set(Calendar.YEAR, year);
                coursesCalendarEnd.set(Calendar.MONTH, month);
                coursesCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
                updateLabelEnd();
            }

            private void updateLabelEnd() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                courseEndEdit.setText(sdf.format(coursesCalendarEnd.getTime()));
            }
        };

    }


    /**
     * Creating the menu options.
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_detailed, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        if (item.getItemId() == R.id.deleteCourse) {
            for (Courses c : repository.getAllCourses()) {
                if (c.getCourseID() == courseID) {
                    Courses pickedCourse = c;
                    repository.delete(pickedCourse);

                } if (item.getItemId() == R.id.Share) {
                    try {
                        EditText courseNoteEdit = findViewById(R.id.courseNoteEdit);
                        String shareNote = courseNoteEdit.getText().toString();

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareNote);
                        shareIntent.putExtra(Intent.EXTRA_TITLE, "Course Notes: ");
                        startActivity(Intent.createChooser(shareIntent, null));
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        }
        return super.onOptionsItemSelected(item);

    }
}