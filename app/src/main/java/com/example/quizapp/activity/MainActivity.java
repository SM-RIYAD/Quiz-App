package com.example.quizapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ScrollingView;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.R;
import com.example.quizapp.classes.QuizDS;
import com.example.quizapp.classes.Widgetcontroller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private View progress_bar_view;
private TextView question_count_text,text_option1,text_option2,text_option3,text_option4,
        temp,text_question,text_score_percentage,text_counter,text_message;
private ScrollView scroll_content;
private ProgressBar progress_score_bar;
private  String quiz_id,selected_ans="";
private ArrayList<QuizDS>arrayList;
private int index=0,score_progress = 0;
private long total_questions;
    private ArrayList<String> user_answers,correct_answers;
private CardView card_answer,card_questions,card_result;
private Button button_quit,button_play;
    private Widgetcontroller widgetcontroller ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_question= findViewById(R.id.text_question);
            quiz_id= getIntent().getStringExtra("quiz_id");
        progress_bar_view= findViewById(R.id.progress_bar_view);
        progress_score_bar=findViewById(R.id.  progress_score_bar);
        text_option1=findViewById(R.id.text_option1);

        text_message=findViewById(R.id. text_message);
        text_option2=findViewById(R.id.text_option2);
        text_counter=findViewById(R.id.text_counter);
        text_option3=findViewById(R.id.text_option3);
        text_option4=findViewById(R.id.text_option4);
        button_quit=findViewById(R.id.button_quit);
        button_play=findViewById(R.id.button_play);
        card_answer=findViewById(R.id.card_answer);
        text_score_percentage= findViewById(R.id. text_score_percentage);
        scroll_content=findViewById(R.id.scroll_content);
        card_questions=findViewById(R.id.card_questions);
        card_result=findViewById(R.id.card_result);
        question_count_text=findViewById(R.id.question_count_text);
        user_answers = new ArrayList<>();
        correct_answers = new ArrayList<>();
        text_counter.setText("1");

getQuizData();
arrayList = new ArrayList<>();
card_answer.setOnClickListener(this);
        text_option1.setOnClickListener(this);
        text_option2.setOnClickListener(this);
        text_option3.setOnClickListener(this);
        text_option4.setOnClickListener(this);
        button_play.setOnClickListener(this);
        button_quit.setOnClickListener(this);

temp=text_option1;
    }

    private void getQuizData() {
        DatabaseReference   databaseReference = FirebaseDatabase.getInstance().getReference("Questions");
databaseReference.child(quiz_id).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        total_questions= snapshot.getChildrenCount();
        question_count_text.setText(MessageFormat.format("0/{0}",total_questions));
        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
          ///  text_question.setText(Objects.requireNonNull(dataSnapshot.child("que").getValue()).toString());
          ///  text_option1.setText(Objects.requireNonNull(dataSnapshot.child("_1").getValue()).toString());
          ////  text_option2.setText(Objects.requireNonNull(dataSnapshot.child("_2").getValue()).toString());
         ///   text_option3.setText(Objects.requireNonNull(dataSnapshot.child("_3").getValue()).toString());
          ////  text_option4.setText(Objects.requireNonNull(dataSnapshot.child("_4").getValue()).toString());
arrayList.add(new QuizDS(Objects.requireNonNull(dataSnapshot.child("que").getValue()).toString(),Objects.requireNonNull(dataSnapshot.child("_1").getValue()).toString(),Objects.requireNonNull(dataSnapshot.child("_2").getValue()).toString(),
        Objects.requireNonNull(dataSnapshot.child("_3").getValue()).toString(),Objects.requireNonNull(dataSnapshot.child("_4").getValue()).toString()));


        }
        widgetcontroller = new Widgetcontroller(progress_bar_view,question_count_text, total_questions);
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
        {   handleClicks(text_option1);




        }

        if(v== button_play){

            resetEverything();
        }
        if (v == button_quit) {
            exitApp();
        }
        if(v==text_option2){
            handleClicks(text_option2);




        }
        if(v==text_option3){
            handleClicks(text_option3);



        }
        if(v==text_option4){
//            temp.setBackgroundTintList(null);
//            temp=text_option4;
//            text_option4.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.purple_500));

            handleClicks(text_option4);


        }
        if(v==card_answer){
            if(selected_ans.isEmpty()){

                Toast.makeText(getApplicationContext(), "Select an option first!", Toast.LENGTH_SHORT).show();
                return;
            }
            widgetcontroller.update_progress();
            card_questions.setLayerType(View.LAYER_TYPE_HARDWARE,null);
            card_questions.animate().scaleX(.3f).scaleY(.2f).setDuration(500).setInterpolator(new OvershootInterpolator());
            scroll_content.animate().alpha(0).setDuration(500);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    card_questions.animate().scaleX(1).scaleY(1).setDuration(500).setInterpolator(new OvershootInterpolator());
                    scroll_content.animate().alpha(1).setDuration(500);
                    user_answers.add(selected_ans);
                    if(index+1==total_questions){
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Answers");
databaseReference.child(quiz_id).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
            correct_answers.add(Objects.requireNonNull(dataSnapshot.getValue()).toString());
        }
        makeScoreUIVisible();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


                        return;
                    }


                    index++;
                    text_counter.setText(String.valueOf(index + 1));
                    assignDataToUI();
                }
            },500);



        }


    }

    private void exitApp() {
        finishAffinity();

    }

    private void resetEverything() {

        index = 0;
        assignDataToUI();
        progress_bar_view.setScaleX(0);
       /// card_score.setVisibility(View.GONE);
        question_count_text.setText(MessageFormat.format("0/{0}", total_questions));
        card_result.setVisibility(View.GONE);
        card_questions.setVisibility(View.VISIBLE);
        card_answer.setVisibility(View.VISIBLE);
        user_answers.clear();
        correct_answers.clear();
        arrayList.clear();
        getQuizData();
        score_progress = 0;
        total_questions = 0;
        progress_bar_view.setScaleX(0);
        text_counter.setText(String.valueOf(index + 1));
    }

    private void makeScoreUIVisible() {
        for (int i=0; i<correct_answers.size(); i++) {

            Toast.makeText(getApplicationContext(),"INSIDE!", Toast.LENGTH_SHORT).show();

            //this portion is not working...for some reason. fix it for BDT 100.
            if (user_answers.get(i).equals(correct_answers.get(i))) {

                score_progress += (100 / correct_answers.size());


            }
        }
        displayMessage();


        text_score_percentage.setText(MessageFormat.format("{0}%", score_progress));
        card_result.setVisibility(View.VISIBLE);
        card_questions.setVisibility(View.GONE);
        card_answer.setVisibility(View.GONE);

        ObjectAnimator progressAnimator;
      progressAnimator = ObjectAnimator.ofInt(progress_score_bar,"progress", 0,score_progress);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new OvershootInterpolator());
        progressAnimator.start();
    }

    private void displayMessage() {
        if(score_progress>65){
            text_message.setText("AWSOME!!");

        }
        if(score_progress<50){
            text_message.setText("You can do better!!");

        }
    }


    private void handleClicks(TextView tv) {
        selected_ans=tv.getText().toString().trim();
        temp.setBackgroundTintList(null);
        temp = tv;
        tv.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.purple_500));
    }
}
