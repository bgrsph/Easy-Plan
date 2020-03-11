package com.example.easyplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    Button logout;
    BottomNavigationView bottomMenu;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mGetReference;
    private static final String TAG = "MyActivity";
    private String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logout = findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("remember");
                editor.commit();
                finish();
                startActivity(new Intent(Profile.this, WelcomePage.class));
                bottomMenu.getMenu().getItem(3).setChecked(true);
            }
        });
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
                    bottomMenu.getMenu().getItem(0).setChecked(true);
                    mDatabase = FirebaseDatabase.getInstance();

                    mGetReference = FirebaseDatabase.getInstance().getReference().child("melikemellow-5fa45").child("user").child("0").child("name");
                    mGetReference.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            a = dataSnapshot.getValue(String.class);
                            Toast.makeText(Profile.this, a, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;
                case R.id.plans:
                    //fragment = new InboxFragment();
                    Toast.makeText(Profile.this, "This is where you see your plans.", Toast.LENGTH_SHORT).show();
                    bottomMenu.getMenu().getItem(1).setChecked(true);
                    break;
                case R.id.help:
                    //Intent intent = new Intent(this, AddActivity.class);
                    Toast.makeText(Profile.this, "If you, for whatever stupid reason, cannot use this app, come here.", Toast.LENGTH_SHORT).show();
                    bottomMenu.getMenu().getItem(2).setChecked(true);
                    //startActivity(intent);
                    return true;
                case R.id.profile:
                    Toast.makeText(Profile.this, "To change your settings.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Profile.this, Profile.class));
                    bottomMenu.getMenu().getItem(3).setChecked(true);
                    //fragment = new HistoryFragment();
                    break;
            }
            return false;
        }
    };

}
