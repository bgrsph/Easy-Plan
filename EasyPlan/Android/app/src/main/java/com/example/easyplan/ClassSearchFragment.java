package com.example.easyplan;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassSearchFragment extends Fragment {

    public static final ArrayList<Course> selectedCourses = new ArrayList<Course>();
    public static TextView text1;
    private String accEmail;
    Button searchClasses;
    RecyclerView recyclerView;
    public static ArrayList<Course> courseList = new ArrayList<>();
    public static ArrayList<Course> courseList1 = new ArrayList<>();
    private SearchView search;
    final CourseAdapter adapter = new CourseAdapter(courseList1);
    View view;

    public ClassSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        accEmail = this.getArguments().getString("accEmail");
        view = inflater.inflate(R.layout.fragment_class_search, container, false);
        new FirebaseHelper("ugradCourses").readData(new FirebaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Course> courses, List<String> keys) {
                boolean add = false;
                for (Course x : courses) {
                    if (courseList.isEmpty()) courseList.add(x);
                    else {
                        for (Course y : courseList) {
                            add = true;
                            if (x.getSubject().equals(y.getSubject()) && x.getCatalog().equals(y.getCatalog())) {
                                add = false;
                                break;
                            }
                        }
                        if (add) courseList.add(x);
                    }
                }
                //courseList = courses;
                updateView(courseList);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
        text1 = view.findViewById(R.id.text1);

        recyclerView = view.findViewById(R.id.courseListView);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };
        recyclerView.setItemAnimator(animator);


        search = view.findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filter(query);
                return false;
            }
        });

        searchClasses = view.findViewById(R.id.searchButton);
        searchClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("courseList", selectedCourses);

                PlanCourseFragment planCourse = new PlanCourseFragment();
                planCourse.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, planCourse);
                transaction.commit();
            }
        });

        text1.setText(selectedCourses.size() + " courses selected.");

        return view;
    }

    private void filter(String text) {
        ArrayList<Course> filteredList = new ArrayList<Course>();
        for (Course x : courseList) {
            if (x.getSubject().toLowerCase().contains(text.toLowerCase()) || x.getCatalog().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(x);
            }
        }
        adapter.filterList(filteredList);
        updateView(filteredList);
    }

    private void updateView(ArrayList<Course> list) {
        CourseAdapter adapter = new CourseAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
