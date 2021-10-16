package com.quocdat.assignment_mob201.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.quocdat.assignment_mob201.database.AsmDB;
import com.quocdat.assignment_mob201.models.User;

public class UserDAO implements IUser{

    AsmDB db;
    public UserDAO(Context context){
        db = AsmDB.getDbInstance(context);
    }
    @Override
    public User login(String _username, String _password) {
        User user = null;
        String q = "SELECT ID, USERNAME, PASSWORD, NAME, ROLE, DOB, PHONE FROM USERS WHERE USERNAME = ?";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(q, new String[]{_username});
        try{
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Integer id = cursor.getInt(cursor.getColumnIndex("ID"));
                    Integer role = cursor.getInt(cursor.getColumnIndex("ROLE"));
                    String username = cursor.getString(cursor.getColumnIndex("USERNAME"));
                    String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
                    String name = cursor.getString(cursor.getColumnIndex("NAME"));
                    String phone = cursor.getString(cursor.getColumnIndex("PHONE"));
                    String dob = cursor.getString(cursor.getColumnIndex("DOB"));

                    if (!password.equals(_password)) break;
                    user = new User();
                    user.setId(id);
                    user.setRole(role);
                    user.setUsername(username);
                    user.setName(name);
                    user.setPhone(phone);
                    user.setDob(dob);

                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.d("login: ", e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return user;
    }

    @Override
    public boolean register(User user) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        long rows = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("USERNAME", user.getUsername());
            values.put("PASSWORD", user.getPassword());
            values.put("NAME", user.getName());
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

        return rows >= 1;
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
            values.put("NAME", user.getName());
            values.put("PHONE", user.getPhone());
            values.put("DOB", user.getDob());
            values.put("ROLE", user.getRole());
            rows = database.update("USERS", values, "ID = ?",
                    new String[]{String.valueOf(user.getId())});

            //Transaction: kiem tra neu insert bi loi se callback lai
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("update: ", e.getMessage());
        }finally {
            database.endTransaction();
        }

        return rows == 1;
    }

    @Override
    public boolean checkUsernameExist(String _username) {
        boolean exist = false;
        String q = "SELECT USERNAME FROM USERS WHERE USERNAME = ?";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(q, new String[]{_username});
        try{
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    exist = true;
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.d("checkUsernameExist: ", e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return exist;
    }
}
