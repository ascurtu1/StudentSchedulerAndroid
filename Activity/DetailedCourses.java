package com.example.studentscheduler.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    Button saveBtnCourse;

    /**
     * Populating the Detailed Courses page with the information from the database.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_courses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Repository repository = new Repository(getApplication());


        courseTitleEdit = findViewById(R.id.courseTitleEdit);
        courseStartEdit = findViewById(R.id.courseStartEdit);
        courseEndEdit = findViewById(R.id.courseEndEdit);
        instructorEdit = findViewById(R.id.instructorEdit);
        instructorEmailEdit = findViewById(R.id.instructorEmailEdit);
        instructorPhoneEdit = findViewById(R.id.instructorPhoneEdit);
        courseNoteEdit = findViewById(R.id.courseNoteEdit);
        courseStatusSpin = findViewById(R.id.courseStatusSpin); //spinner
        termSpin = findViewById(R.id.termSpin); //spinner

        String myFormat = "MM/dd/yy";
        SimpleDateFormat startDate = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat endDate = new SimpleDateFormat(myFormat, Locale.US);
        courseStartEdit.setText(startDate.format(new Date()));
        courseEndEdit.setText(endDate.format(new Date()));

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

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.courseStatus, android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpin.setAdapter(statusAdapter);
        String currentStatus = courseStatus;
        int intStatus = statusAdapter.getPosition(currentStatus);
        courseStatusSpin.setSelection(intStatus);


        /** Populating the spinner for course associated term. */

        List<Terms> termListSpinner = repository.getAllTerms();

        ArrayAdapter<Terms> termAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termListSpinner);
        termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpin.setAdapter(termAdapter);
        termSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                Terms selectedTerm = termListSpinner.get(i);
                termID = selectedTerm.getTermID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        for (int i = 0; i < termListSpinner.size(); i++) {
            if (termListSpinner.get(i).getTermID() == termID) {
                termSpin.setSelection(i);
                break;
            }
        }


/**
 * The below methods allow for the creation of a calendar to allow the user to insert course start and end dates.
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

        /**
         * Method allows the user to click the save button and either save a new course or update a current course.
         */
        saveBtnCourse = findViewById(R.id.saveCourseBtn);
        saveBtnCourse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String courseTitleProvided = courseTitleEdit.getText().toString();
                String courseDateProvided = courseStartEdit.getText().toString();
                String courseDateEnteredEnd = courseEndEdit.getText().toString();
                String courseInstructor = instructorEdit.getText().toString();
                String courseInstructorEmail = instructorEmailEdit.getText().toString();
                String courseInstructorPhone = instructorPhoneEdit.getText().toString();
                String courseNote = courseNoteEdit.getText().toString();
                String courseStatus = courseStatusSpin.getSelectedItem().toString();

                Repository repository = new Repository(getApplication());
                if (courseID != -1) {
                    Courses course = new Courses(courseID, termID, courseTitleProvided, courseDateProvided, courseDateEnteredEnd, courseStatus, courseInstructor, courseInstructorEmail, courseInstructorPhone, courseNote);
                    repository.update(course);
                } else {
                    Courses course = new Courses(0, termID, courseTitleProvided, courseDateProvided, courseDateEnteredEnd, courseStatus, courseInstructor, courseInstructorEmail, courseInstructorPhone, courseNote);
                    repository.insert(course);
                }

                Intent intent = new Intent(DetailedCourses.this, CoursesList.class);
                startActivity(intent);
            }
        });
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
        if (item.getItemId() == R.id.homePage) {
                Intent intent = new Intent(DetailedCourses.this, MainActivity.class);
                startActivity(intent);

                return true;
            }

        if (item.getItemId() == R.id.deleteCourse) {
                Repository repository = new Repository(getApplication());
                Courses pickedCourse = new Courses(courseID, termID,"","","","","","","","");
                repository.delete(pickedCourse);
                Toast.makeText(this,"The course has been deleted.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailedCourses.this, CoursesList.class);
                startActivity(intent);
                return true;
            }


     if (item.getItemId() == R.id.Share) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, courseNoteEdit.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Note");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            } if (item.getItemId() == R.id.notifyStart) {
                String startNotify = courseStartEdit.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat, Locale.US);

                try {
                    Date startDate2 = sdf2.parse(startNotify);
                    String courseName = courseTitleEdit.getText().toString();


                    long startTrigger = startDate2.getTime();
                    Intent startIntent = new Intent(DetailedCourses.this, Receiver.class);
                    startIntent.putExtra("key", "Course: " + courseName + " starts today!");
                    PendingIntent startPendingIntent = PendingIntent.getBroadcast(DetailedCourses.this, MainActivity.numAlert++, startIntent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManagerStart = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManagerStart.set(AlarmManager.RTC_WAKEUP, startTrigger, startPendingIntent);

                    return true;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                String endNotify = courseEndEdit.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf3 = new SimpleDateFormat(myFormat, Locale.US);

                try {
                    Date endDate2 = sdf3.parse(endNotify);
                    String courseName = courseTitleEdit.getText().toString();


                    long endDateTrigger = endDate2.getTime();
                    Intent endDateIntent = new Intent(DetailedCourses.this, Receiver.class);
                    endDateIntent.putExtra("key", "Course: " + courseName + " ends today!");
                    PendingIntent endPendingIntent = PendingIntent.getBroadcast(DetailedCourses.this, MainActivity.numAlert++, endDateIntent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManagerEndDate = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManagerEndDate.set(AlarmManager.RTC_WAKEUP, endDateTrigger, endPendingIntent);

                    return true;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            return super.onOptionsItemSelected(item);

        }
    }




