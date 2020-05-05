package com.example.easyplan;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseInfoAdapter extends RecyclerView.Adapter<CourseInfoAdapter.CourseInfoViewHolder> {
    Context context;
    ArrayList<CourseInfoItemHelper> courses;

    public CourseInfoAdapter(Context context, ArrayList<CourseInfoItemHelper> courses) {
        this.context = context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public CourseInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.course_info_item, parent, false);
        // Return a new holder instance
        CourseInfoAdapter.CourseInfoViewHolder viewHolder = new CourseInfoAdapter.CourseInfoViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseInfoViewHolder holder, int position) {
            CourseInfoItemHelper helper = courses.get(position);
            holder.courseName.setText(helper.getCourseName() + " - " + helper.getSection());
            holder.instructor.setText(helper.getInstructor());
            holder.meetingTime.setText(helper.getMeetingTime());

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class CourseInfoViewHolder extends RecyclerView.ViewHolder{
        TextView courseName, instructor, meetingTime;

        public CourseInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.course_info_name);
            instructor = itemView.findViewById(R.id.course_info_instr_name);
            meetingTime = itemView.findViewById(R.id.course_section_meeting_time);
        }
    }
}
