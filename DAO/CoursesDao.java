package com.example.studentscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentscheduler.Entity.Courses;
import com.example.studentscheduler.Entity.Terms;

import java.util.List;

@Dao
public interface CoursesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Courses courses);

    @Update
    void update(Courses courses);

    @Delete
    void delete(Courses courses);

    @Query("SELECT * FROM Courses ORDER BY CourseID ASC")
    List<Courses> getAllCourses();

    @Query("SELECT * FROM Courses WHERE termID = :termID")
    List<Courses> getAssociatedCourses(int termID);


}

