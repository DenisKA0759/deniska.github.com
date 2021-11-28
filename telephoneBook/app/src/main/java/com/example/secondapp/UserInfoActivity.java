package com.example.secondapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {
    TextView textViewInfoName;
    TextView textViewInfoLastName;
    TextView textViewInfoPhone;
    Button removeUserInfo;
    Button editUserInfo;
    Users users;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        users = new Users(UserInfoActivity.this);
        user = (User) getIntent().getSerializableExtra("user");
        removeUserInfo = findViewById(R.id.removeUserInfo);
        editUserInfo = findViewById(R.id.editUserInfo);
        editUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfoActivity.this, UserFormActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);

            }
        });
        removeUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                users.removeUser(user.getUuid());
                onBackPressed();
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        user = users.getUserFromDB(user.getUuid());
        textViewInfoName = findViewById(R.id.textViewInfoName);
        textViewInfoLastName = findViewById(R.id.textViewInfoLastName);
        textViewInfoPhone = findViewById(R.id.textViewInfoPhone);
        textViewInfoName.setText(user.getUserName());
        textViewInfoLastName.setText(user.getUserLastName());
        textViewInfoPhone.setText(user.getPhone());
    }
}