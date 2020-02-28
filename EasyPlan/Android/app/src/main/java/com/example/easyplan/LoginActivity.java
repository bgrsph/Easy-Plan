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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logEmail = findViewById(R.id.logEmail);
        logPass = findViewById(R.id.logPassword);
        loginBtn = findViewById(R.id.logButton);
        logSign = findViewById(R.id.logSign);



        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email, pass;
                email = logEmail.getText().toString();
                if(!email.contains("@ku.edu.tr")){
                    //buraya register gibi TOAST ekleyebiliriz.
                }else {
                    //buraya register gibi TOAST ekleyebiliriz.
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
