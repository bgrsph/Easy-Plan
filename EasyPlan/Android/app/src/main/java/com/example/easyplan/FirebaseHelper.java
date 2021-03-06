package com.example.easyplan;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.easyplan.ClassSearchFragment.labList;

public class FirebaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mGetReference;
    private String s;
    public ArrayList<Course> courseList = new ArrayList<>();

    public FirebaseHelper(String ref) {
        mDatabase = FirebaseDatabase.getInstance();
        mGetReference = mDatabase.getReference(ref);
        s = ref;
    }

    public interface DataStatus {
        void DataIsLoaded(ArrayList<Course> courses, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public void readData(final DataStatus dataStatus) {
        mGetReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (s.equals("ugradCourses")) {
                    courseList.clear();
                    List<String> keys = new ArrayList<>();
                    for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                        keys.add(keyNode.getKey());
                        Course course = keyNode.getValue(Course.class);
                        courseList.add(course);
                    }
                    dataStatus.DataIsLoaded(courseList, keys);
                } else if (s.equals("ugradCoursesLab")) {
                    labList.clear();
                    List<String> keys = new ArrayList<>();
                    for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                        keys.add(keyNode.getKey());
                        Course course = keyNode.getValue(Course.class);
                        labList.add(course);
                    }
                    dataStatus.DataIsLoaded(labList, keys);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MERT", "ON CANCELLED");
            }
        });
    }

}
