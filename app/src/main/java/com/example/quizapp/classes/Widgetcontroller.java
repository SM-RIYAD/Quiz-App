package com.example.quizapp.classes;

import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import java.text.MessageFormat;

public class Widgetcontroller {

    public View progress_bar_view;
    private TextView question_count_text;
    private  double scale =0.04;
public int count =1;
    public Widgetcontroller(View progress_bar_view, TextView question_count_text) {
        this.progress_bar_view = progress_bar_view;
        this.question_count_text= question_count_text;

    }

    public void update_progress() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
          question_count_text.setText(MessageFormat.format("{0}/25", count));
          count++;
                progress_bar_view.animate().scaleX((float)scale).setDuration(700).setInterpolator(new OvershootInterpolator());
                ///   progress_bar_view.setScaleX((float) scale);
                if(scale>1){return;}
                scale+=0.04;

                update_progress();

            }
        },1000);



    }
}
