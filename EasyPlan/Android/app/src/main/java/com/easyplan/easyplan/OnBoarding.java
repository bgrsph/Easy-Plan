package com.easyplan.easyplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoarding extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout dots_layout;
    TextView[] dots;
    Button startBtn;
    SliderAdapter sliderAdapter;
    Animation animation;
    int currPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.slider);
        dots_layout = findViewById(R.id.onBoardingDots);
        startBtn = findViewById(R.id.onBoardingStartBtn);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void skip(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    public void goToLogin(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    public void next(View view){
        viewPager.setCurrentItem(currPos+1);
    }

    private void addDots(int position){
        dots = new TextView[4];
        dots_layout.removeAllViews();

        for(int i=0; i< dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots_layout.addView(dots[i]);
        }
        if(dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.button_red));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currPos = position;
            if(position != 3){
                startBtn.setVisibility(View.INVISIBLE);
            }else{
                animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.right_animation);
                startBtn.setAnimation(animation);
                startBtn.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
