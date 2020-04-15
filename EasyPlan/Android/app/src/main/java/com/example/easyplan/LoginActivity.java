package com.example.easyplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText logEmail, logPass;
    Button loginBtn;
    TextView logSign;
    TextView logForget;
    Switch logRemember;
    FirebaseAuth auth;
//    public static LoginActivity la;
//
//    public static LoginActivity getInstance(){
//        if(la == null){
//            la = new LoginActivity();
//        }
//        return la;
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logEmail = findViewById(R.id.logEmail);
        logPass = findViewById(R.id.logPassword);
        loginBtn = findViewById(R.id.logButton);
        logSign = findViewById(R.id.logSign);
        logForget = findViewById(R.id.forgetPass);
        logRemember = findViewById(R.id.loginRemember);
        auth = FirebaseAuth.getInstance();

        SharedPreferences sp = getSharedPreferences("checkbox", MODE_PRIVATE);
        String check = sp.getString("remember", "");
        if(check.equals("true")){
            startActivity(new Intent(LoginActivity.this, Mainpage.class));
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;
                email = logEmail.getText().toString();
                pass = logPass.getText().toString();
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Please enter an email.", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                }else if(!email.contains("@ku.edu.tr")){
                    Toast.makeText(LoginActivity.this, "Please use your KU e-mail.", Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = auth.getCurrentUser();
                            if(task.isSuccessful()){
                                if(user.isEmailVerified())
                                    {logEmail.setText("");
                                    logPass.setText("");
                                    startActivity(new Intent(LoginActivity.this, Mainpage.class));
                                }else {
                                    Toast.makeText(LoginActivity.this, "Please verify your e-mail.", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "Email and password do not match.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });

        logForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(logEmail.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Enter your email to email field.", Toast.LENGTH_LONG).show();
                }else{
                    auth.sendPasswordResetEmail(logEmail.getText().toString()).addOnCompleteListener(LoginActivity.this,
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "Email sent", Toast.LENGTH_SHORT).show();                        }
                                }
                            });
                }

            }
        });

        logRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences sp = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("remember", "true");
                    editor.apply();

                }else if(!buttonView.isChecked()){
                    SharedPreferences sp = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });


    }




    public void goToSignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUp.class);
        startActivity(intent);
    }
}
