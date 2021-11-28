package com.example.secondapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.secondapp.database.UserBaseHelper;
import com.example.secondapp.database.UserDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Users {
    private ArrayList<User> userList;
    private SQLiteDatabase database;
    private Context context;

    public Users(Context context) {
        this.context = context.getApplicationContext();
        this.database = new UserBaseHelper(context).getWritableDatabase();
    }
    public void addUser(User user){
        ContentValues values = getContentValues(user);
        database.insert(UserDbSchema.UserTable.NAME, null,values);
    }
    private static ContentValues getContentValues(User user){
        ContentValues values = new ContentValues();
        // Сопоставляем колонки и свойства объекта User
        values.put(UserDbSchema.Cols.UUID, user.getUuid().toString());
        values.put(UserDbSchema.Cols.USERNAME, user.getUserName());
        values.put(UserDbSchema.Cols.USERLASTNAME, user.getUserLastName());
        values.put(UserDbSchema.Cols.PHONE, user.getPhone());
        return values;
    }

    private UserCursorWrapper queryUsers(){
        Cursor cursor = database.query(UserDbSchema.UserTable.NAME,null,null,null,null,null,null);
        return new UserCursorWrapper(cursor);
    }

    public ArrayList<User> getUserList(){
        this.userList = new ArrayList<User>();
        UserCursorWrapper cursorWrapper = queryUsers();
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                User user = cursorWrapper.getUser();
                userList.add(user);
                cursorWrapper.moveToNext();

            }
        }finally {
            cursorWrapper.close();
        }
        return userList;
    }
    public User getUserFromDB(UUID uuid){
        Cursor cursor = database.query(UserDbSchema.UserTable.NAME,
                null,
                UserDbSchema.Cols.UUID+"=?",
                new String[]{uuid.toString()},
                null, null, null);
        UserCursorWrapper cursorWrapper = new UserCursorWrapper(cursor);
        cursorWrapper.moveToFirst();
        return cursorWrapper.getUser();
    }
    public void removeUser(UUID uuid){
        String stringUuid = uuid.toString();
        database.delete(UserDbSchema.UserTable.NAME,
                UserDbSchema.Cols.UUID+"=?", new String[]{stringUuid});
    }
    public void updateUser(User user){
        ContentValues values = getContentValues(user);
        String stringUuid = user.getUuid().toString();
        database.update(UserDbSchema.UserTable.NAME,
                values,
                UserDbSchema.Cols.UUID+"=?", new String[]{stringUuid}
        );
    }
}
