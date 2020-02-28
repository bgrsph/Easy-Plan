package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText logEmail, logPass;
    Button loginBtn;
    TextView logSign;
    TextView loginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logEmail = findViewById(R.id.logEmail);
        logPass = findViewById(R.id.logPassword);
        loginBtn = findViewById(R.id.logButton);
        logSign = findViewById(R.id.logSign);
        loginError = findViewById(R.id.loginError);



        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email, pass;
                email = logEmail.getText().toString();
                if(!email.contains("@ku.edu.tr")){
                    loginError.setVisibility(View.VISIBLE);
                }else {
                    loginError.setVisibility(View.INVISIBLE);
                }
                pass = logPass.getText().toString();


            }
        });

    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUp.class);
        startActivity(intent);
    }
}
