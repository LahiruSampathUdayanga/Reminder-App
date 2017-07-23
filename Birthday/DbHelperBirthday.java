package com.example.sampath.reminder.Birthday;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by SAMPATH on 5/19/2017.
 */

public class DbHelperBirthday extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "birthday.db";
    public static final String TABLE_NAME = "tableBd";
    public static final String COL1 = "ID";
    public static final String COL2 = "USERNAME";
    public static final String COL3 = "DOFBIRTHDAY";


    public DbHelperBirthday(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT , USERNAME TEXT , DOFBIRTHDAY INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    public boolean addUser(String username, String birthday){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, username);
        contentValues.put(COL3, birthday);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return data;
    }

    public void deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(id);
        db.delete(TABLE_NAME, COL1 + " = ?", new String[]{ String.valueOf(id)});
        db.close();
    }
/*
    Cursor getReminder(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL1, COL2, COL3}, COL1 + "=?", new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }*/

}
