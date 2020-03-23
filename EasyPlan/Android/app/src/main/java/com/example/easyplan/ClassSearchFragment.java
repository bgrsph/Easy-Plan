package com.example.easyplan;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassSearchFragment extends Fragment {


    TextView text;
    private String accEmail;
    Button searchClasses;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mGetReference;


    public ClassSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        accEmail = this.getArguments().getString("accEmail");
        View view = inflater.inflate(R.layout.fragment_class_search, container, false);

        mDatabase = FirebaseDatabase.getInstance();
        mGetReference = mDatabase.getReference().child("user");
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
}
