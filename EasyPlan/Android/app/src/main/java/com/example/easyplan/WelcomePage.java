package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class WelcomePage extends AppCompatActivity {
    Button signUpButton;
    Button loginButton;
    VideoView vv;

    @Override
    protected void onResume(){
        super.onResume();
        vv =  (VideoView) findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.koc_tanitim;
        Uri uri = Uri.parse(videoPath);
        vv.setVideoURI(uri);
        vv.start();


        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);

            }

        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        signUpButton = findViewById(R.id.welcomeSignUP);
        loginButton = findViewById(R.id.welcomeLogin);

        vv =  (VideoView) findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.koc_tanitim;
        Uri uri = Uri.parse(videoPath);
        vv.setVideoURI(uri);
        vv.start();


        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);

            }

        });

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
