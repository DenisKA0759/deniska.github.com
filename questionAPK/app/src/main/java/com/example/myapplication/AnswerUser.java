package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class AnswerUser extends AppCompatActivity {
    private TextView text;
    private Button repeatBtn;
    private Button closeBtn;
    private int trueAnswer;
    private String[] userQuestion = new String[10];
    private String[] answerUser = new String[10];
    private String[] answerUser2 = new String[2];
    private int[] i1 = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_user);

        text = findViewById(R.id.textView2);
        repeatBtn = findViewById(R.id.repeatBtn);
        closeBtn = findViewById(R.id.closeBtn);
        text.setMovementMethod(new ScrollingMovementMethod());
        userQuestion = getIntent().getStringArrayExtra("userQuestion");
        answerUser = getIntent().getStringArrayExtra("answerUser");
        for (String str : answerUser) {
            answerUser2=str.split(" - ");
            if (answerUser2[1].equals("правильно")) {
                trueAnswer++;
            }
        }
        text.append("Вы заработали " + String.valueOf(trueAnswer * 10) + " баллов из 100" + "\n" +
                "Ваши ответы: " + "\n");
        for (int i = 0; i < userQuestion.length; i++) {
            text.append(userQuestion[i]+ " - " + answerUser[i] + "\n");}

        repeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restart();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MainActivity.finish();
                finish();
            }
        });
    }

        @Override
        public void onSaveInstanceState (Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            Log.d("SYSTEM INFO", "Метод onSaveInstanceState() запущен");
        }

    public void Restart() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

}
