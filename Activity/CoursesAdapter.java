package com.example.studentscheduler.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentscheduler.Entity.Courses;
import com.example.studentscheduler.Entity.Terms;
import com.example.studentscheduler.R;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {

    private List<Courses> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CoursesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class CoursesViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        public CoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Courses pickedCourse = mCourses.get(position);
                    Intent intent = new Intent(context, DetailedCourses.class);
                    intent.putExtra("Course ID", pickedCourse.getCourseID());
                    intent.putExtra("Term ID", pickedCourse.getTermID());
                    intent.putExtra("Title", pickedCourse.getCourseTitle());
                    intent.putExtra("Start", pickedCourse.getCourseStart());
                    intent.putExtra("End", pickedCourse.getCourseEnd());
                    intent.putExtra("Status", pickedCourse.getCourseStatus());
                    intent.putExtra("Instructor", pickedCourse.getCourseInstructorName());
                    intent.putExtra("Instructor Phone", pickedCourse.getCourseInstructorPhone());
                    intent.putExtra("Instructor Email", pickedCourse.getCourseInstructorEmail());
                    intent.putExtra("Notes", pickedCourse.getCourseNote());
                    context.startActivity(intent);
                }
            });

        }
    }

    public CoursesAdapter.CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CoursesViewHolder(itemView);
    }


    public void onBindViewHolder(@NonNull CoursesAdapter.CoursesViewHolder holder, int position) {
        if (mCourses != null) {
            Courses current = mCourses.get(position);
            String title = current.getCourseTitle();
            holder.courseItemView.setText(title);
        } else {
            holder.courseItemView.setText("No courses currently available");
        }
    }

    public void setCourses(List<Courses> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if(mCourses!=null) {
            return mCourses.size();
        }
        else return 0;
    }

}