package com.example.easyplan;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    TextInputLayout email, password;
    Button signupButton;
    TextView error;
    TextInputLayout firstName;
    TextInputLayout lastName;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        signupButton = findViewById(R.id.signupButton);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);

        login = findViewById(R.id.regLogin);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = firstName.getEditText().getText().toString();
                String lName = lastName.getEditText().getText().toString();
                String mail = email.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                if (fName.length() == 0) {
                    Toast.makeText(SignUp.this, "First name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (lName.length() == 0) {
                    Toast.makeText(SignUp.this, "Last name cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (mail.length() == 0) {
                    Toast.makeText(SignUp.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!mail.contains("@ku.edu.tr")) {
                    Toast.makeText(SignUp.this, "You can sign up only with a valid KU e-mail.", Toast.LENGTH_LONG).show();
                } else if (pass.length() < 6) {
                    Toast.makeText(SignUp.this, "Password must be at least 6 characters.", Toast.LENGTH_LONG).show();
                } else {
                    auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "This e-mail is already used.",
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                final FirebaseUser user = auth.getCurrentUser();
                                user.sendEmailVerification().addOnCompleteListener(SignUp.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignUp.this, "Verification is sent to your e-mail adress.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                                startActivity(new Intent(SignUp.this, LoginActivity.class));
                                finish();
                            }
                        }
                    });
                }


            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, LoginActivity.class));
                finish();
            }
        });
    }

}
