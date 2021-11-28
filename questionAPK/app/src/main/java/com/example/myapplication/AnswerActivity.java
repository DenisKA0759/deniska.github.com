package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {
    TextView answerTextView;
    private boolean inAnswerTrue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        answerTextView=findViewById(R.id.answerTextView);
        inAnswerTrue=getIntent().getBooleanExtra("answer",false);
        answerTextView.setText(inAnswerTrue? "да": "нет");
    }
}