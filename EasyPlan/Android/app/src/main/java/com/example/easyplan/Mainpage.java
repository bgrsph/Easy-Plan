package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Mainpage extends AppCompatActivity {

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        logout = findViewById(R.id.logoutBtn);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Mainpage.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
            }
        });
    }
}
