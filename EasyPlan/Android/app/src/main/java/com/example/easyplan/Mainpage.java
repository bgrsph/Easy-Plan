package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Mainpage extends AppCompatActivity {

    Button logout;
    BottomNavigationView bottomMenu;

    private String accEmail;
    private List<Course> courseList;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mGetReference;
    //private FirebaseHelper a = new FirebaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        accEmail = getIntent().getStringExtra("accEmail");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        bottomMenu = findViewById(R.id.NavigationBot);
        bottomMenu.setOnNavigationItemSelectedListener(NavigationItemSelectedListener);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bottomMenu.setVisibility(View.INVISIBLE);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            bottomMenu.setVisibility(View.VISIBLE);
        }


        Bundle bundle = new Bundle();
        mDatabase = FirebaseDatabase.getInstance();
        mGetReference = mDatabase.getReference("asda");

        Log.d("DENEME", "sonunda oldu amk");

        //bundle.putString("course1", a.userList.get(0).name);

        bundle.putString("accEmail", accEmail );
        ClassSearchFragment classSearchFragment = new ClassSearchFragment();
        classSearchFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.fragment, classSearchFragment, "ClassSearch");

        fragmentTransaction1.commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener NavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(MenuItem item){
            switch (item.getItemId()){
                case R.id.search:

                    Bundle bundle = new Bundle();
                    bundle.putString("accEmail", accEmail );

                    ClassSearchFragment classSearchFragment = new ClassSearchFragment();
                    classSearchFragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.fragment, classSearchFragment, "ClassSearch");
                    fragmentTransaction1.commit();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                    return true;
                case R.id.plans:
                    //fragment = new InboxFragment();
//                    Toast.makeText(Mainpage.this, "This is where you see your plans.", Toast.LENGTH_SHORT).show();
                    PlansFragment plansFragment = new PlansFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.fragment, plansFragment, "Plans");
                    fragmentTransaction2.commit();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                    return true;
                case R.id.help:
                    //Intent intent = new Intent(this, AddActivity.class);
//                    Toast.makeText(Mainpage.this, "If you, for whatever stupid reason, cannot use this app, come here.", Toast.LENGTH_SHORT).show();
                    HelpFragment helpFragment = new HelpFragment();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fragment, helpFragment, "Plans");
                    fragmentTransaction3.commit();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                    return true;
                case R.id.profile:
//                    Toast.makeText(Mainpage.this, "To change your settings.", Toast.LENGTH_SHORT).show();
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.fragment, profileFragment, "Plans");
                    fragmentTransaction4.commit();
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                    return true;
            }
            return true;
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
