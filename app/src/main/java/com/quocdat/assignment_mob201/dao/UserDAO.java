package com.quocdat.assignment_mob201.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.quocdat.assignment_mob201.database.AsmDB;
import com.quocdat.assignment_mob201.models.Course;
import com.quocdat.assignment_mob201.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUser{

    AsmDB db;
    public UserDAO(Context context){
        db = AsmDB.getDbInstance(context);
    }
    @Override
    public boolean login(String usernam, String password) {
        return false;
    }

    @Override
    public boolean register(User user) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        long rows = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("USERNAME", user.getUsername());
            values.put("PASSWOED", user.getPassword());
            values.put("NAME", user.getNameUser());
            values.put("PHONE", user.getPhone());
            values.put("DOB", user.getDob());
            values.put("ROLE", user.getRole());
            rows = database.insertOrThrow("USERS", null, values);

            //Transaction: kiem tra neu insert bi loi se callback lai
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("insert: ", e.getMessage());
        }finally {
            database.endTransaction();
        }

        return rows == 1;
    }

    @Override
    public boolean update(User user) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        long rows = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("USERNAME", user.getUsername());
            values.put("PASSWOED", user.getPassword());
            values.put("NAME", user.getNameUser());
            values.put("PHONE", user.getPhone());
            values.put("DOB", user.getDob());
            values.put("ROLE", user.getRole());
            rows = database.update("USERS", values, "ID = ?",
                    new String[]{String.valueOf(user.getId_user())});

            //Transaction: kiem tra neu insert bi loi se callback lai
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("update: ", e.getMessage());
        }finally {
            database.endTransaction();
        }

        return rows == 1;
    }
}
