package com.example.easyplan;

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

public class Mainpage extends AppCompatActivity {

    Button logout;
    BottomNavigationView bottomMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        bottomMenu = findViewById(R.id.NavigationBot);
        bottomMenu.setOnNavigationItemSelectedListener(NavigationItemSelectedListener);
        //logout = findViewById(R.id.logoutBtn);


        //logout.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View v) {
         //       SharedPreferences sp = getSharedPreferences("checkbox", MODE_PRIVATE);
           //     SharedPreferences.Editor editor = sp.edit();
             // editor.commit();
               // finish();
           // }
        //});

    }

    private BottomNavigationView.OnNavigationItemSelectedListener NavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.search:
                    //fragment = new HomeFragment();
                    Toast.makeText(Mainpage.this, "This is where you search for courses.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.plans:
                    //fragment = new InboxFragment();
                    Toast.makeText(Mainpage.this, "This is where you see your plans.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.help:
                    //Intent intent = new Intent(this, AddActivity.class);
                    Toast.makeText(Mainpage.this, "If you, for whatever stupid reason, cannot use this app, come here.", Toast.LENGTH_SHORT).show();
                    //startActivity(intent);
                    return true;
                case R.id.settings:
                    Toast.makeText(Mainpage.this, "To change your settings.", Toast.LENGTH_SHORT).show();
                    //fragment = new HistoryFragment();
                    break;
            }
            return false;
        }
    };
}
