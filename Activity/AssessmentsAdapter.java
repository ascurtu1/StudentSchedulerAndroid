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

import com.example.studentscheduler.Entity.Assessments;
import com.example.studentscheduler.Entity.Terms;
import com.example.studentscheduler.R;

import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentsViewHolder> {

    private List<Assessments> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class AssessmentsViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;
        public AssessmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessments pickedAssessment = mAssessments.get(position);
                    Intent intent = new Intent(context, DetailedAssessments.class);
                    intent.putExtra("ID", pickedAssessment.getAssessmentID());
                    intent.putExtra("CourseID", pickedAssessment.getCourseID());
                    intent.putExtra("Title", pickedAssessment.getAssessmentTitle());
                    intent.putExtra("Type", pickedAssessment.getAssessmentType());
                    intent.putExtra("Start", pickedAssessment.getAssessmentStart());
                    intent.putExtra("End", pickedAssessment.getAssessmentEnd());
                    context.startActivity(intent);
                }
            });

        }
    }


    public AssessmentsAdapter.AssessmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentsViewHolder(itemView);
    }


    public void onBindViewHolder(@NonNull AssessmentsAdapter.AssessmentsViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessments current = mAssessments.get(position);
            String title = current.getAssessmentTitle();
            holder.assessmentItemView.setText(title);
        } else {
            holder.assessmentItemView.setText("No terms currently available");
        }
    }


    public void setAssessments(List<Assessments> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if(mAssessments!=null) {
            return mAssessments.size();
        }
        else return 0;
    }

}