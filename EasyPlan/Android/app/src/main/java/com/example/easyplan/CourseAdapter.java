package com.example.easyplan;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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

import static com.example.easyplan.ClassSearchFragment.selectedCourses;
import static com.example.easyplan.ClassSearchFragment.text1;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private ArrayList<Course> courseList1 = new ArrayList<Course>();
    private ArrayList<Course> filterList = new ArrayList<Course>();
    private int size = 0;

    public CourseAdapter(ArrayList<Course> courses) {
        courseList1 = courses;
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
                    if (imageView.getTag() == "plus") {
                        imageView.setImageResource(R.drawable.check);
                        imageView.setTag("check");
                        for (Course x : ClassSearchFragment.allSections) {
                            if ((x.getSubject() + x.getCatalog()).equals(nameTextView.getTag())) {
                                selectedCourses.add(x);
                            }
                        }
                        size++;
                    } else {
                        imageView.setImageResource(R.drawable.plus);
                        imageView.setTag("plus");
                        List<Course> courseDelete = new ArrayList<Course>();
                        for (Course x : selectedCourses) {
                            if ((x.getSubject() + x.getCatalog()).equals(nameTextView.getTag())) {
                                courseDelete.add(x);
                            }
                        }
                        for (Course x : courseDelete) {
                            if (selectedCourses.contains(x)) selectedCourses.remove(x);
                        }
                        size--;
                    }

                    text1.setText(size + " courses selected.");
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
        Course course = courseList1.get(position);

        // Set item views based on your views and data model
        TextView textView1 = holder.nameTextView;
        textView1.setText(course.getSubject() + course.getCatalog());
        textView1.setTag(course.getSubject() + course.getCatalog());
        ImageView image = holder.imageView;
        for (Course x : ClassSearchFragment.selectedCourses) {
            if ((x.getSubject() + x.getCatalog()).equals(textView1.getTag())) {
                image.setImageResource(R.drawable.check);
                image.setTag("check");
            }
        }
        if (image.getTag() != "check") {
            image.setImageResource(R.drawable.plus);
            image.setTag("plus");
        }
    }

    @Override
    public int getItemCount() {
        return courseList1.size();
    }

    public void filterList(ArrayList<Course> filteredList) {
        courseList1.clear();
        courseList1 = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
