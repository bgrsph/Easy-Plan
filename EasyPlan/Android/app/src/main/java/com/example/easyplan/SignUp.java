package com.example.easyplan;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {


    EditText email, password, passwordAgain;
    Button signupButton;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordAgain = findViewById(R.id.passwordAgain);
        signupButton = findViewById(R.id.signupButton);
        error = findViewById(R.id.error);

    }

    public void signUp (View view) {
        //implement try-catch ?
        String a = password.getText().toString();
        String b = passwordAgain.getText().toString();
        if(!email.getText().toString().contains("@ku.edu.tr")){
            error.setText("Please enter a valid KU e-mail address.");
            error.setVisibility(View.VISIBLE);
        } else if (!a.equals(b)){
                error.setText("The passwords do not match.");
                error.setVisibility(View.VISIBLE);
        } else {
            error.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(SignUp.this, MainActivity.class);
            startActivity(intent);
        }

    }

}
