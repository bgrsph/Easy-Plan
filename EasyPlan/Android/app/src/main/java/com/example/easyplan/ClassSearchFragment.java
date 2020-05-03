package com.example.easyplan;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassSearchFragment extends Fragment {

    public static final ArrayList<Course> selectedCourses = new ArrayList<Course>();
    public static final ArrayList<Course> selectedLabs = new ArrayList<Course>();
    public static final ArrayList<Course> noDupList = new ArrayList<>();
    public static TextView text1;
    private String accEmail;
    Button searchClasses, unselector;
    RecyclerView recyclerView;
    public static ArrayList<Course> courseList = new ArrayList<>();
    public static ArrayList<Course> labList = new ArrayList<>();
    public static ArrayList<Course> courseList1 = new ArrayList<>();
    public static ArrayList<Course> allSections = new ArrayList<>();
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
        final RelativeLayout rLay = view.findViewById(R.id.loadingPanel);

        new FirebaseHelper("ugradCourses").readData(new FirebaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Course> courses, List<String> keys) {
                allSections = courses;
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
                rLay.setVisibility(View.GONE);
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

        new FirebaseHelper("ugradCoursesLab").readData(new FirebaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Course> courses, List<String> keys) {
                //allSections = courses;
                boolean add = false;
                for (Course x : courses) {
                    if (labList.isEmpty()) labList.add(x);
                    else {
                        for (Course y : labList) {
                            add = true;
                            if (x.getSubject().equals(y.getSubject()) && x.getCatalog().equals(y.getCatalog())) {
                                add = false;
                                break;
                            }
                        }
                        if (add) labList.add(x);
                    }
                }
                //courseList = courses;
                rLay.setVisibility(View.GONE);
                //updateView(courseList);
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
                bundle.putParcelableArrayList("labsList", selectedLabs);

                PlanCourseFragment planCourse = new PlanCourseFragment();
                planCourse.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, planCourse);
                transaction.commit();
            }
        });

        unselector = view.findViewById(R.id.unselectAll);
        unselector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCourses.clear();
                noDupList.clear();
                updateView(courseList);
                text1.setText(noDupList.size() + " courses selected.");
            }
        });

        String key = "";
        for (Course x : selectedCourses) {
            if (key.equals("")) {
                noDupList.add(x);
                key = x.getSubject() + x.getCatalog();
            } else if (!key.equals(x.getSubject() + x.getCatalog())) {
                noDupList.add(x);
                key = x.getSubject() + x.getCatalog();
            }
        }
        text1.setText(noDupList.size() + " courses selected.");

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
