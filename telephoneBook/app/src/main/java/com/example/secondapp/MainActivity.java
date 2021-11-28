package com.example.secondapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<User> userList = new ArrayList<>();
    UserAdapter userAdapter;
    Button addUserBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        addUserBtn = findViewById(R.id.addUserBtn);
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // вызов активити редактирования элемента юзера
                Intent intent = new Intent(MainActivity.this, UserFormActivity.class);
                startActivity(intent);
            }
        });
    }
    private void recyclerViewInit(){
        Users users = new Users(MainActivity.this);
        userList = users.getUserList();
        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        recyclerViewInit();
    }

    private  class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView itemTextView;
        User user;
        public UserHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.single_item, viewGroup, false));
            // itemView - текущий layout single_item
            itemTextView = itemView.findViewById(R.id.itemTextView);
            itemView.setOnClickListener(this);
        }

        public void bind(String userString, User user){
            itemTextView.setText(userString);
            this.user = user;
        }

        @Override
        public void onClick(View view) { // клик по элементам списка
            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }
    private class UserAdapter extends RecyclerView.Adapter<UserHolder>{
        ArrayList<User> users;
        public UserAdapter(ArrayList<User> users) {
            this.users = users;
        }
        @Override
        public UserHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            return new UserHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(UserHolder userHolder, int position) {
            User user = users.get(position);
            String userString = user.getUserName()+"\n"+user.getUserLastName();
            userHolder.bind(userString, user);
        }

        @Override
        public int getItemCount() {
            return users.size();
        }
    }
}