package com.example.secondapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserFormActivity extends AppCompatActivity {
    Button insertUserBtn;
    EditText editTextName;
    EditText editTextLastName;
    EditText editTextPhone;
    User user;
    static boolean b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        user = (User) getIntent().getSerializableExtra("user");
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhone = findViewById(R.id.editTextPhone);
        insertUserBtn = findViewById(R.id.insertUserBtn);
        if(user != null){ // Если редактируем юзера
            editTextName.setText(user.getUserName());
            editTextLastName.setText(user.getUserLastName());
            editTextPhone.setText(user.getPhone());b=true;
        }else{ // если добавляем нового
            user = new User();b=false;
        }

        insertUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setUserName(editTextName.getText().toString());
                user.setUserLastName(editTextLastName.getText().toString());
                user.setPhone(editTextPhone.getText().toString());
                Users users = new Users(UserFormActivity.this);
                if (b==true) users.updateUser(user);
                if (b==false) users.addUser(user);

                onBackPressed();
            }
        });

    }
}