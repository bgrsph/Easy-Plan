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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText logEmail, logPass;
    Button loginBtn;
    TextView logSign;
    TextView logForget;
    Switch logRemember;
    FirebaseAuth auth;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mGetReference;

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
        signInButton = findViewById(R.id.loginGoogle);

        mDatabase = FirebaseDatabase.getInstance();
        mGetReference = mDatabase.getReference().child("coursesTest");
        mGetReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SharedPreferences sp = getSharedPreferences("checkbox", MODE_PRIVATE);
        String check = sp.getString("remember", "");
        if(check.equals("true")){
            startActivity(new Intent(LoginActivity.this, Mainpage.class));
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 1);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(LoginActivity.this, Mainpage.class);
            String a = account.getEmail();
            intent.putExtra("accEmail", a);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUp.class);
        startActivity(intent);
    }
}
