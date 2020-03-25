package com.example.easyplan;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassSearchFragment extends Fragment {


    TextView text;
    private String accEmail;
    Button searchClasses;
    RecyclerView recyclerView;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mGetReference;
    private List<Course> courseList;
    View view;

    public ClassSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        accEmail = this.getArguments().getString("accEmail");
        view = inflater.inflate(R.layout.fragment_class_search, container, false);

        /*mDatabase = FirebaseDatabase.getInstance();
        mGetReference = mDatabase.getReference().child("coursesTest");
        mGetReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */

        new FirebaseHelper().readData(new FirebaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Course> courses, List<String> keys) {
                courseList = courses;
                updateView();
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



        searchClasses = view.findViewById(R.id.searchButton);
        searchClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mDatabase.getReference().child("user").child("0").child("name").getValue(String.Class);
            }
        });


        return view;
    }

    private void updateView() {
        recyclerView = view.findViewById(R.id.courseListView);
        // Create adapter passing in the sample user data
        CourseAdapter adapter = new CourseAdapter(courseList);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
