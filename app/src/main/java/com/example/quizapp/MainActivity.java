package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class MainActivity extends AppCompatActivity {
private View progress_bar_view;
private  double scale =0.025;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress_bar_view= findViewById(R.id.progress_bar_view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress_bar_view.setScaleX((float) scale);

            }
        },1000);



update_progress();
    }

    private void update_progress() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress_bar_view.animate().scaleX((float)scale).setDuration(700).setInterpolator(new OvershootInterpolator());
             ///   progress_bar_view.setScaleX((float) scale);
                scale+=0.025;
                if(scale>1){return;}
                update_progress();

            }
        },1000);



    }
}