package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.example.quizapp.classes.Widgetcontroller;

public class MainActivity extends AppCompatActivity {
private View progress_bar_view;
private TextView question_count_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress_bar_view= findViewById(R.id.progress_bar_view);
        question_count_text=findViewById(R.id.question_count_text);
new Widgetcontroller(progress_bar_view,question_count_text).update_progress();
    }





    }
