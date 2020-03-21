package com.example.easyplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Mainpage extends AppCompatActivity {

    Button logout;
    BottomNavigationView bottomMenu;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mGetReference;
    private static final String TAG = "MyActivity";
    private String accEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        accEmail = getIntent().getStringExtra("accEmail");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        bottomMenu = findViewById(R.id.NavigationBot);
        bottomMenu.setOnNavigationItemSelectedListener(NavigationItemSelectedListener);
        Bundle bundle = new Bundle();
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
          /*          //fragment = new HomeFragment();
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
                    });*/
                    Bundle bundle = new Bundle();
                    bundle.putString("accEmail", accEmail );

                    ClassSearchFragment classSearchFragment = new ClassSearchFragment();
                    classSearchFragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.fragment, classSearchFragment, "ClassSearch");
                    fragmentTransaction1.commit();

                    return true;
                case R.id.plans:
                    //fragment = new InboxFragment();
//                    Toast.makeText(Mainpage.this, "This is where you see your plans.", Toast.LENGTH_SHORT).show();
                    PlansFragment plansFragment = new PlansFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.fragment, plansFragment, "Plans");
                    fragmentTransaction2.commit();
                    return true;
                case R.id.help:
                    //Intent intent = new Intent(this, AddActivity.class);
//                    Toast.makeText(Mainpage.this, "If you, for whatever stupid reason, cannot use this app, come here.", Toast.LENGTH_SHORT).show();
                    HelpFragment helpFragment = new HelpFragment();
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fragment, helpFragment, "Plans");
                    fragmentTransaction3.commit();
                    return true;
                case R.id.profile:
//                    Toast.makeText(Mainpage.this, "To change your settings.", Toast.LENGTH_SHORT).show();
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.fragment, profileFragment, "Plans");
                    fragmentTransaction4.commit();
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
