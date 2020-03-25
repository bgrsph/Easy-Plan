package com.example.easyplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> implements Filterable {

    private ArrayList<Course> courseList = new ArrayList<Course>();
    private ArrayList<Course> filterList = new ArrayList<Course>();
    private CustomFilter filter;


    public CourseAdapter(ArrayList<Course> courses) {
        courseList = courses;
        filterList = courses;
    }

    @Override
    public Filter getFilter() {
        if(filter == null)
        {
            filter = new CustomFilter(filterList,this);
        }

        return filter;
    }

    public void setFilter(List<Course> filterList) {
        filter = new CustomFilter(filterList,this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public Button messageButton;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.courseName);
            messageButton = itemView.findViewById(R.id.addButton);
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
        Button button = holder.messageButton;
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

    public class CustomFilter extends Filter {

        CourseAdapter adapter;
        List<Course> filterList;

        public CustomFilter(List<Course> filterList, CourseAdapter adapter)
        {
            this.adapter = adapter;
            this.filterList = filterList;

        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0)
            {
                //CHANGE TO UPPER
                constraint = constraint.toString().toUpperCase();
                //STORE OUR FILTERED PLAYERS
                List<Course> filteredCourses = new ArrayList<Course>();

                for (int i = 0; i < filterList.size(); i++)
                {
                    //CHECK
                    if (filterList.get(i).getCatalog().toUpperCase().contains(constraint) || filterList.get(i).getSubject().toUpperCase().contains(constraint))
                    {
                        //ADD PLAYER TO FILTERED PLAYERS
                        filteredCourses.add(filterList.get(i));
                    }
                }

                results.count = filteredCourses.size();
                results.values = filteredCourses;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            adapter.courseList = (ArrayList<Course>) results.values;
            adapter.notifyDataSetChanged();
        }
    }

}
