package com.example.secondapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UserBaseHelper extends SQLiteOpenHelper { // класс для построения БД
    private static final int VERSION = 3;
    private static final  String DTABASE_NAME = "userBase.db";
    public UserBaseHelper(Context context) {
        super(context, DTABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+UserDbSchema.UserTable.NAME+"(" +
                "_id integer primary key autoincrement, " +
                UserDbSchema.Cols.UUID+", " +
                UserDbSchema.Cols.USERNAME+", " +
                UserDbSchema.Cols.USERLASTNAME+", " +
                UserDbSchema.Cols.PHONE+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
