package com.example.quizapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class quizId extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_quizID;
    private Button button_attend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_id);
        edit_quizID =findViewById(R.id.edit_quizID);
        button_attend = findViewById(R.id.button_attend);
button_attend.setOnClickListener(this);
    }
    private void verifyQuizid(){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("QuizID");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              for(DataSnapshot dataSnapshot:snapshot.getChildren()){


                  if(Objects.requireNonNull(dataSnapshot.getValue()).toString().equals(edit_quizID.getText().toString().trim())){

                      Toast.makeText(getApplicationContext(), "Exists!", Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("quiz_id",edit_quizID.getText().toString().trim()));
                     finish();
return;
                  }

                /// Toast.makeText(getApplicationContext(), Objects.requireNonNull(dataSnapshot.child("ID").getValue()).toString(),Toast.LENGTH_SHORT).show();

              }
                Toast.makeText(getApplicationContext(), "doesn't exists!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClick(View v) {
if(v==button_attend){
if(edit_quizID.getText().toString().trim().isEmpty()){
    Snackbar.make(button_attend,"field can not be empty",Snackbar.LENGTH_SHORT).show();
    return;
    }
verifyQuizid();



}
    }
}