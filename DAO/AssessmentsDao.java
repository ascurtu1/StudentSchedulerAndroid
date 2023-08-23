package com.example.studentscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentscheduler.Entity.Assessments;
import com.example.studentscheduler.Entity.Terms;

import java.util.List;

@Dao
public interface AssessmentsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessments assessments);

    @Update
    void update(Assessments assessments);

    @Delete
    void delete(Assessments assessments);

    @Query("SELECT * FROM Assessments ORDER BY AssessmentID ASC")
    List<Assessments> getAllAssessments();

    @Query("SELECT * FROM Assessments WHERE AssessmentID = :courseID")
    List<Assessments> getAssociatedAssessments(int courseID);

}

