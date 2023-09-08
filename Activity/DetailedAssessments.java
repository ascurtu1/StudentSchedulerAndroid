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
import com.example.studentscheduler.Entity.Assessments;
import com.example.studentscheduler.Entity.Courses;
import com.example.studentscheduler.Entity.Terms;
import com.example.studentscheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedAssessments extends AppCompatActivity {

    EditText assessmentTitleEdit;
    EditText assessmentStartEdit;
    EditText assessmentEndEdit;
    Spinner assessmentTypeSpin;
    Spinner courseSpin;
    String assessmentTitle;
    String assessmentStart;
    String assessmentEnd;
    String assessmentType;
    int courseID;
    int assessmentID;
    Button saveAssessmentBtn;



    Repository repository;
    final Calendar assessmentCalendarStart = Calendar.getInstance();
    final Calendar assessmentCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener assessmentStartDateCalendar;
    DatePickerDialog.OnDateSetListener assessmentEndDateCalendar;



    /**
     * Populating the Detailed Assessments page with the information from the database.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        assessmentTitleEdit = findViewById(R.id.assessmentTitleEdit);
        assessmentStartEdit = findViewById(R.id.assessmentStartEdit);
        assessmentEndEdit = findViewById(R.id.assessmentEndEdit);
        assessmentTypeSpin = findViewById(R.id.assessmentTypeSpin); //spinner
        courseSpin = findViewById(R.id.courseSpin); //spinner

        String myFormat = "MM/dd/yy";
        SimpleDateFormat startDate = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat endDate = new SimpleDateFormat(myFormat, Locale.US);
        assessmentStartEdit.setText(startDate.format(new Date()));
        assessmentEndEdit.setText(endDate.format(new Date()));

        assessmentTitle = getIntent().getStringExtra("Title");
        assessmentStart = getIntent().getStringExtra("Start");
        assessmentEnd = getIntent().getStringExtra("End");
        assessmentType = getIntent().getStringExtra("Type");
        assessmentID= getIntent().getIntExtra("ID", -1);
        courseID = getIntent().getIntExtra("CourseID", -1);

        assessmentTitleEdit.setText(assessmentTitle);
        assessmentStartEdit.setText(assessmentStart);
        assessmentEndEdit.setText(assessmentEnd);

        repository = new Repository(getApplication());




        /** Populating the spinner for assessment type. */

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.assessmentType, android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpin.setAdapter(typeAdapter);
        String typePicked = assessmentType;
        int setType = typeAdapter.getPosition(typePicked);
        assessmentTypeSpin.setSelection(setType);


        /** Populating the spinner for associated course. */

        List<Courses> courseListSpinner = repository.getAllCourses();

        ArrayAdapter<Courses> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseListSpinner);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpin.setAdapter(courseAdapter);
        courseSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                Courses selectedCourse = courseListSpinner.get(i);
                courseID = selectedCourse.getCourseID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        for (int i = 0; i < courseListSpinner.size(); i++) {
            if (courseListSpinner.get(i).getCourseID() == courseID) {
                courseSpin.setSelection(i);
                break;
            }
        }

 /** The below methods allow for the creation of a calendar to allow the user to insert assessment start and end dates.
 */
        assessmentStartEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String dateProvided = assessmentStartEdit.getText().toString();
                if (dateProvided.equals("")) dateProvided = "09/01/23";
                try {
                    assessmentCalendarStart.setTime(startDate.parse(dateProvided));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(DetailedAssessments.this, assessmentStartDateCalendar, assessmentCalendarStart.get(Calendar.YEAR),
                        assessmentCalendarStart.get(Calendar.MONTH), assessmentCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        assessmentEndEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateEnteredEnd = assessmentEndEdit.getText().toString();
                if (dateEnteredEnd.equals("")) dateEnteredEnd = "09/01/23";
                try {
                    assessmentCalendarEnd.setTime(endDate.parse(dateEnteredEnd));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(DetailedAssessments.this, assessmentEndDateCalendar, assessmentCalendarEnd.get(Calendar.YEAR),
                        assessmentCalendarEnd.get(Calendar.MONTH), assessmentCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        assessmentStartDateCalendar = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                assessmentCalendarStart.set(Calendar.YEAR, year);
                assessmentCalendarStart.set(Calendar.MONTH, month);
                assessmentCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                assessmentStartEdit.setText(startDate.format(assessmentCalendarStart.getTime()));
                updateLabelStart();
            }

            private void updateLabelStart() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                assessmentStartEdit.setText(sdf.format(assessmentCalendarStart.getTime()));
            }
        };


        assessmentEndDateCalendar = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                assessmentCalendarEnd.set(Calendar.YEAR, year);
                assessmentCalendarEnd.set(Calendar.MONTH, month);
                assessmentCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
                updateLabelEnd();
            }

            private void updateLabelEnd() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                assessmentEndEdit.setText(sdf.format(assessmentCalendarEnd.getTime()));
            }
        };

        /**
         * Method allows the user to click the save button and either save a new assessment or update a current one.
         */
        saveAssessmentBtn = findViewById(R.id.saveAssessmentBtn);
        saveAssessmentBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String aTitleProvided = assessmentTitleEdit.getText().toString();
                String aStartDateProvided = assessmentStartEdit.getText().toString();
                String aEndDateProvided = assessmentEndEdit.getText().toString();
                String aTypeProvided = assessmentTypeSpin.getSelectedItem().toString();

                Repository repository = new Repository(getApplication());
                if (assessmentID != -1) {
                    Assessments assessment = new Assessments(assessmentID, courseID, aTitleProvided, aTypeProvided, aStartDateProvided, aEndDateProvided);
                    repository.update(assessment);
                } else {
                    Assessments assessment = new Assessments(0, courseID, aTitleProvided, aTypeProvided, aStartDateProvided, aEndDateProvided);
                    repository.insert(assessment);
                }

                Intent intent = new Intent(DetailedAssessments.this, AssessmentsList.class);
                startActivity(intent);
            }
        });
    }


    /**
     * Creating the menu options.
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_detailed, menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        if (item.getItemId() == R.id.homePage) {
            Intent intent = new Intent(DetailedAssessments.this, MainActivity.class);
            startActivity(intent);

            return true;

        } if (item.getItemId() == R.id.deleteAssessment) {
            Repository repository = new Repository(getApplication());
            Assessments pickedAssess = new Assessments(assessmentID, courseID,"","","","");
            repository.delete(pickedAssess);
            Toast.makeText(this,"The assessment has been deleted.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailedAssessments.this, AssessmentsList.class);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.notifyStartAssess) {
            String startNotify = assessmentStartEdit.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat, Locale.US);

            try {
                Date assessStartDate = sdf2.parse(startNotify);
                String assessName = assessmentTitleEdit.getText().toString();


                long startTrigger = assessStartDate.getTime();
                Intent startIntent = new Intent(DetailedAssessments.this, Receiver.class);
                startIntent.putExtra("key", "Assessment: " + assessName + " starts today!");
                PendingIntent startPendingIntent = PendingIntent.getBroadcast(DetailedAssessments.this, MainActivity.numAlert++, startIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManagerStart = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerStart.set(AlarmManager.RTC_WAKEUP, startTrigger, startPendingIntent);

                return true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            String endNotify = assessmentEndEdit.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf3 = new SimpleDateFormat(myFormat, Locale.US);

            try {
                Date assessmentEndDate = sdf3.parse(endNotify);
                String assessName = assessmentTitleEdit.getText().toString();


                long endDateTrigger = assessmentEndDate.getTime();
                Intent endDateIntent = new Intent(DetailedAssessments.this, Receiver.class);
                endDateIntent.putExtra("key", "Course: " + assessName + " ends today!");
                PendingIntent endPendingIntent = PendingIntent.getBroadcast(DetailedAssessments.this, MainActivity.numAlert++, endDateIntent, PendingIntent.FLAG_IMMUTABLE);
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
