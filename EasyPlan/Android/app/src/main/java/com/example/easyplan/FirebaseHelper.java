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

public class FirebaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mGetReference;
    public List<User> userList = new ArrayList<>();

    public FirebaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mGetReference = mDatabase.getReference("user");
    }

    public interface DataStatus {
        void DataIsLoaded(List<User> courses, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public void readData(final DataStatus dataStatus) {
        mGetReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    User course = keyNode.getValue(User.class);
                    userList.add(course);
                }
                dataStatus.DataIsLoaded(userList, keys);
                Log.d("MERT", "ON DATA CHANGE DONE");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MERT", "ON CANCELLED");
            }
        });
    }

}
