package com.example.easyplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mainpage extends AppCompatActivity {

    Button logout;
    BottomNavigationView bottomMenu;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mGetReference;
    private static final String TAG = "MyActivity";
    private String a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        bottomMenu = findViewById(R.id.NavigationBot);
        bottomMenu.setOnNavigationItemSelectedListener(NavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener NavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(MenuItem item)
        {
            switch (item.getItemId()){
                case R.id.search:
                    //fragment = new HomeFragment();
                    //Toast.makeText(Mainpage.this, "This is where you search for courses.", Toast.LENGTH_SHORT).show();
                    mDatabase = FirebaseDatabase.getInstance();

                    mGetReference = FirebaseDatabase.getInstance().getReference().child("melikemellow-5fa45").child("user").child("0").child("name");
                    mGetReference.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                a = dataSnapshot.getValue(String.class);
                            Toast.makeText(Mainpage.this, a, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    bottomMenu.getMenu().getItem(0).setChecked(true);
                    break;
                case R.id.plans:
                    //fragment = new InboxFragment();
                    Toast.makeText(Mainpage.this, "This is where you see your plans.", Toast.LENGTH_SHORT).show();
                    bottomMenu.getMenu().getItem(1).setChecked(true);
                    break;
                case R.id.help:
                    //Intent intent = new Intent(this, AddActivity.class);
                    Toast.makeText(Mainpage.this, "If you, for whatever stupid reason, cannot use this app, come here.", Toast.LENGTH_SHORT).show();
                    bottomMenu.getMenu().getItem(2).setChecked(true);
                    //startActivity(intent);
                    return true;
                case R.id.profile:
                    Toast.makeText(Mainpage.this, "To change your settings.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Mainpage.this, Profile.class));
                    bottomMenu.getMenu().getItem(3).setChecked(true);
                    //fragment = new HistoryFragment();
                    break;
            }
            return false;
        }
    };

    /*private ValueEventListener ab = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Toast.makeText(Mainpage.this, "onChildChanged", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };*/

}
