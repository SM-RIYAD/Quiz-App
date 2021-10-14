package com.example.quizapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ScrollingView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.classes.QuizDS;
import com.example.quizapp.classes.Widgetcontroller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private View progress_bar_view;
private TextView question_count_text,text_option1,text_option2,text_option3,text_option4,temp,text_question;
private ScrollView scroll_content;
private  String quiz_id;
private ArrayList<QuizDS>arrayList;
private int index=0;

private CardView card_answer,card_questions;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_question= findViewById(R.id.text_question);
            quiz_id= getIntent().getStringExtra("quiz_id");
        progress_bar_view= findViewById(R.id.progress_bar_view);
        text_option1=findViewById(R.id.text_option1);
        text_option2=findViewById(R.id.text_option2);
        text_option3=findViewById(R.id.text_option3);
        text_option4=findViewById(R.id.text_option4);
        card_answer=findViewById(R.id.card_answer);
        scroll_content=findViewById(R.id.scroll_content);
        card_questions=findViewById(R.id.card_questions);
        question_count_text=findViewById(R.id.question_count_text);
new Widgetcontroller(progress_bar_view,question_count_text).update_progress();
getQuizData();
arrayList = new ArrayList<>();
card_answer.setOnClickListener(this);
        text_option1.setOnClickListener(this);
        text_option2.setOnClickListener(this);
        text_option3.setOnClickListener(this);
        text_option4.setOnClickListener(this);

temp=text_option1;
    }

    private void getQuizData() {
        DatabaseReference   databaseReference = FirebaseDatabase.getInstance().getReference("Questions");
databaseReference.child(quiz_id).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
          ///  text_question.setText(Objects.requireNonNull(dataSnapshot.child("que").getValue()).toString());
          ///  text_option1.setText(Objects.requireNonNull(dataSnapshot.child("_1").getValue()).toString());
          ////  text_option2.setText(Objects.requireNonNull(dataSnapshot.child("_2").getValue()).toString());
         ///   text_option3.setText(Objects.requireNonNull(dataSnapshot.child("_3").getValue()).toString());
          ////  text_option4.setText(Objects.requireNonNull(dataSnapshot.child("_4").getValue()).toString());
arrayList.add(new QuizDS(Objects.requireNonNull(dataSnapshot.child("que").getValue()).toString(),Objects.requireNonNull(dataSnapshot.child("_1").getValue()).toString(),Objects.requireNonNull(dataSnapshot.child("_2").getValue()).toString(),
        Objects.requireNonNull(dataSnapshot.child("_3").getValue()).toString(),Objects.requireNonNull(dataSnapshot.child("_4").getValue()).toString()));


        }
        assignDataToUI();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

    }

    private void assignDataToUI() {
        text_question.setText(arrayList.get(index).getQue());
        text_option1.setText(arrayList.get(index).get_1());
        text_option2.setText(arrayList.get(index).get_2());
        text_option3.setText(arrayList.get(index).get_3());
        text_option4.setText(arrayList.get(index).get_4());



    }


    @Override
    public void onClick(View v) {

        if(v==text_option1)
        {             temp.setBackgroundTintList(null);
            temp=text_option1;
            text_option1.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.purple_500));




        }
        if(v==text_option2){
            temp.setBackgroundTintList(null);
              temp=text_option2;
            text_option2.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.purple_500));




        }
        if(v==text_option3){
            temp.setBackgroundTintList(null);
            temp=text_option3;
            text_option3.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.purple_500));



        }
        if(v==text_option4){
            temp.setBackgroundTintList(null);
            temp=text_option4;
            text_option4.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.purple_500));




        }
        if(v==card_answer){
            card_questions.setLayerType(View.LAYER_TYPE_HARDWARE,null);
            card_questions.animate().scaleX(.3f).scaleY(.2f).setDuration(500).setInterpolator(new OvershootInterpolator());
            scroll_content.animate().alpha(0).setDuration(500);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    card_questions.animate().scaleX(1).scaleY(1).setDuration(500).setInterpolator(new OvershootInterpolator());
                    scroll_content.animate().alpha(1).setDuration(500);
                    index++;
                    assignDataToUI();
                }
            },500);



        }


    }
}
