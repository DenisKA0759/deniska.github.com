package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button yesBtn;
    private Button noBtn;
    private TextView text;
    private Button answerBtn; // подсказка
    private Question[] questions = {
            new Question(R.string.question0, true),
            new Question(R.string.question1, true),
            new Question(R.string.question2, false),
            new Question(R.string.question3, false),
            new Question(R.string.question4, false),
            new Question(R.string.question5, true),
            new Question(R.string.question6, true),
            new Question(R.string.question7, true),
            new Question(R.string.question8, false),
            new Question(R.string.question9, true)};
     private String[] userQuestion = new String[questions.length];
    String[] answerUser =new String[questions.length];
    private int questionIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            questionIndex = savedInstanceState.getInt("questionIndex", 0);
            answerUser=savedInstanceState.getStringArray("answerUser");
        }

        Log.d("SYSTEM INFO", "Метод onCreate() запущен");
        setContentView(R.layout.activity_main);

        yesBtn = (Button) findViewById(R.id.yesButton);
        noBtn = (Button) findViewById(R.id.noButton);
        text = (TextView) findViewById(R.id.question);
        text.setText(questions[questionIndex].getQuestion());
        answerBtn = (Button) findViewById(R.id.answerButton);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (questions[questionIndex].isAnswer()) {
                    Toast.makeText(MainActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
                    answerUser[questionIndex]="да - правильно";
                } else {
                    Toast.makeText(MainActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
                    answerUser[questionIndex]="да - неправильно";
                }
                questionIndex = (questionIndex + 1) % questions.length;
                text.setText(questions[questionIndex].getQuestion());

                if (questionIndex == 0) {
                    Intent();
                }
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!questions[questionIndex].isAnswer()) {
                    Toast.makeText(MainActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
                    answerUser[questionIndex]="нет - правильно";
                } else {
                    Toast.makeText(MainActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
                    answerUser[questionIndex]="нет - неправильно";
                }
                questionIndex = (questionIndex + 1) % questions.length;
                text.setText(questions[questionIndex].getQuestion());

                if (questionIndex == 0) {
                    Intent();
                }
            }
        });

        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnswerActivity.class);
                intent.putExtra("answer", questions[questionIndex].isAnswer());
                startActivity(intent);
            }
        });
    }

        public void Intent (){
            Intent intent = new Intent(MainActivity.this, AnswerUser.class);
            int[] d=new int[questions.length];
            for(int i=0;i<questions.length;i++){
                d[i]=questions[i].getQuestion();
                text.setText(d[i]);
                userQuestion[i] = (text.getText().toString());
            }
            intent.putExtra("userQuestion",userQuestion);
            intent.putExtra("answerUser",answerUser);
           startActivity(intent);
           finish();
        }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("SYSTEM INFO", "Метод onStart() запущен");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SYSTEM INFO", "Метод onResume() запущен");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.d("SYSTEM INFO", "Метод onSaveInstanceState() запущен");
        savedInstanceState.putInt("questionIndex", questionIndex);
        savedInstanceState.putStringArray("answerUser",answerUser);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SYSTEM INFO", "Метод onPause() запущен");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SYSTEM INFO", "Метод onStop() запущен");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SYSTEM INFO", "Метод onDestroy() запущен");
    }
}
