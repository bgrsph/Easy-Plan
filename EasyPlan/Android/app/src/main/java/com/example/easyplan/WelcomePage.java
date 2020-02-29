package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WelcomePage extends AppCompatActivity {
    Button signUpButton;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        signUpButton = findViewById(R.id.welcomeSignUP);
        loginButton = findViewById(R.id.welcomeLogin);

        SharedPreferences sp = getSharedPreferences("checkbox", MODE_PRIVATE);
        String remembered = sp.getString("remember", "");
        if(remembered.equals("true")){
            startActivity(new Intent(WelcomePage.this, Mainpage.class));
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, SignUp.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
