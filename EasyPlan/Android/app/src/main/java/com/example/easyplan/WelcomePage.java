package com.example.easyplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class WelcomePage extends AppCompatActivity {
    /*    Button signUpButton;
        Button loginButton;*/
    Animation bottomAnim;
    VideoView vv;
    SharedPreferences onBoardingPref;
    SharedPreferences loggedIn;
    private static int SPLASH_SCREEN = 3000;
    @Override
    protected void onResume() {
        super.onResume();
        vv = (VideoView) findViewById(R.id.videoView);
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
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        final TextView appName = findViewById(R.id.mainTextView);
        appName.setAnimation(bottomAnim);
       /* signUpButton = findViewById(R.id.welcomeSignUP);
        loginButton = findViewById(R.id.welcomeLogin);*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        vv = (VideoView) findViewById(R.id.videoView);
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                onBoardingPref = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime = onBoardingPref.getBoolean("firstTime", true);
                if(isFirstTime){
                    intent = new Intent(WelcomePage.this, OnBoarding.class);
                }else{
                    SharedPreferences sp = getSharedPreferences("userPref", MODE_PRIVATE);
                    String remembered = sp.getString("remember", "");
                    if(!remembered.equals("true")){
                        intent = new Intent(WelcomePage.this, LoginActivity.class);
                    }else{
                        intent = new Intent(WelcomePage.this, Mainpage.class);
                    }

                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

  /*      SharedPreferences sp = getSharedPreferences("userPref", MODE_PRIVATE);
        String remembered = sp.getString("remember", "");
        if (remembered.equals("true")) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
            startActivity(new Intent(WelcomePage.this, Mainpage.class));
            finish();
        }
*/
     /*   signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomePage.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

    }
}
