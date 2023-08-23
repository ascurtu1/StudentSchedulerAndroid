package com.example.studentscheduler.Database;

import android.app.Application;

import com.example.studentscheduler.DAO.AssessmentsDao;
import com.example.studentscheduler.DAO.CoursesDao;
import com.example.studentscheduler.DAO.TermsDao;
import com.example.studentscheduler.Entity.Assessments;
import com.example.studentscheduler.Entity.Courses;
import com.example.studentscheduler.Entity.Terms;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private TermsDao mTermsDao;
    private CoursesDao mCoursesDao;
    private AssessmentsDao mAssessmentsDao;
    private List<Terms> mAllTerms;
    private List<Courses> mAllCourses;
    private List<Courses> mAssociatedCourses;
    private List<Assessments> mAllAssessments;
    private List<Assessments> mAssociatedAssessments;


    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Constructor for the database.
     */
    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mTermsDao = db.termsDao();
        mCoursesDao = db.coursesDao();
        mAssessmentsDao = db.assessmentsDao();
    }

    /**
     * Creating methods for the application to retrieve all the information from the database.
     */

    public List<Terms> getAllTerms() {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermsDao.getAllTerms();

        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }


    public List<Courses> getAllCourses() {
        databaseExecutor.execute(() -> {
            mAllCourses = mCoursesDao.getAllCourses();

        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<Assessments> getAllAssessments() {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentsDao.getAllAssessments();

        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    /**
     * Creating methods to allow the insertion of data into the database.
     */

    public void insert(Terms terms) {
        databaseExecutor.execute(() -> {
            mTermsDao.insert(terms);

        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void insert(Courses courses) {
        databaseExecutor.execute(() -> {
            mCoursesDao.insert(courses);

        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void insert(Assessments assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentsDao.insert(assessments);

        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * Creating methods to allow the updating of data in the database.
     */

    public void update(Terms terms) {
        databaseExecutor.execute(() -> {
            mTermsDao.update(terms);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Courses courses) {
        databaseExecutor.execute(() -> {
            mCoursesDao.update(courses);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessments assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentsDao.update(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creating methods to allow the deletion of data in the database.
     */

    public void delete(Terms terms) {
        databaseExecutor.execute(() -> {
            mTermsDao.delete(terms);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Courses courses) {
        databaseExecutor.execute(() -> {
            mCoursesDao.delete(courses);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessments assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentsDao.delete(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creating methods to retrieve associated courses and assessments from the database.
     */

    public List<Courses> getAssociatedCourses(int termID) {
        databaseExecutor.execute(() -> {
            mAssociatedCourses = mCoursesDao.getAssociatedCourses(termID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssociatedCourses;
    }

    public List<Assessments> getAssociatedAssessments(int courseID) {
        databaseExecutor.execute(() -> {
            mAssociatedAssessments = mAssessmentsDao.getAssociatedAssessments(courseID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAssociatedAssessments;
    }
}

