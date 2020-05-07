package com.example.easyplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleContentAdapter extends RecyclerView.Adapter<ScheduleContentAdapter.ScheduleContentViewHolder> {
    Context context;
    ArrayList<ScheduleContentItem> contents;

    public ScheduleContentAdapter(Context context, ArrayList<ScheduleContentItem> contents) {
        this.context = context;
        this.contents = contents;
    }

    @NonNull
    @Override
    public ScheduleContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleContentViewHolder(LayoutInflater.from(context).inflate(R.layout.schedule_content_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleContentViewHolder holder, int position) {
        holder.courseName.setText(contents.get(position).getCourseName());
        holder.instructorName.setText(contents.get(position).getInstructorName());
        holder.meetingTime.setText(contents.get(position).getMeetingTime());
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    class ScheduleContentViewHolder extends RecyclerView.ViewHolder{
        TextView courseName, instructorName, meetingTime;
        public ScheduleContentViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.course_name_text_view);
            instructorName  = (TextView) itemView.findViewById(R.id.course_instructor_name);
            meetingTime  = (TextView) itemView.findViewById(R.id.course_meeting_time);
        }
    }

}
