package com.example.studentscheduler.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.studentscheduler.DAO.AssessmentsDao;
import com.example.studentscheduler.DAO.CoursesDao;
import com.example.studentscheduler.DAO.TermsDao;
import com.example.studentscheduler.Entity.Assessments;
import com.example.studentscheduler.Entity.Courses;
import com.example.studentscheduler.Entity.Terms;

@Database(entities = {Terms.class, Courses.class, Assessments.class}, version=2, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermsDao termsDao();

    public abstract CoursesDao coursesDao();

    public abstract AssessmentsDao assessmentsDao();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "StudentSchedulerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;

    }
}
