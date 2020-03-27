package com.example.easyplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private ArrayList<Course> courseList = new ArrayList<Course>();
    private ArrayList<Course> filterList = new ArrayList<Course>();

    public CourseAdapter(ArrayList<Course> courses) {
        courseList = courses;
        filterList = courses;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public ImageView imageView;
        public LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.courseName);
            imageView = itemView.findViewById(R.id.image);
            layout = itemView.findViewById(R.id.layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.class_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = courseList.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(course.getSubject() + course.getCatalog());
        ImageView image = holder.imageView;
        image.setImageResource(R.drawable.plus);
        //button.setText(course.isOnline() ? "Message" : "Offline");
        //button.setEnabled(course.isOnline());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void filterList(ArrayList<Course> filteredList) {
        courseList.clear();
        courseList = filteredList;
        notifyDataSetChanged();
    }

}
