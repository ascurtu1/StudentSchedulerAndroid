package com.example.studentscheduler.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentscheduler.Entity.Terms;
import com.example.studentscheduler.R;

import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermsViewHolder> {

    private List<Terms> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class TermsViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        public TermsViewHolder(@NonNull View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Terms pickedTerm=mTerms.get(position);
                    Intent intent = new Intent(context, DetailedTerms.class);
                    intent.putExtra("ID", pickedTerm.getTermID());
                    intent.putExtra("Title", pickedTerm.getTermTitle());
                    intent.putExtra("Start", pickedTerm.getTermStart());
                    intent.putExtra("End", pickedTerm.getTermEnd());
                    context.startActivity(intent);
                }
            });

        }
    }


    public TermsAdapter.TermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermsViewHolder(itemView);
    }


    public void onBindViewHolder(@NonNull TermsAdapter.TermsViewHolder holder, int position) {
        if (mTerms != null) {
            Terms current = mTerms.get(position);
            String title = current.getTermTitle();
            holder.termItemView.setText(title);
        } else {
            holder.termItemView.setText("No terms currently available");
        }
    }



    public void setTerms(List<Terms> terms) {
            mTerms = terms;
            notifyDataSetChanged();
        }

    public int getItemCount() {
        if(mTerms!=null) {
            return mTerms.size();
        }
        else return 0;
    }

    }

