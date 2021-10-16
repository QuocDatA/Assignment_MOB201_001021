package com.quocdat.assignment_mob201.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.quocdat.assignment_mob201.database.AsmDB;
import com.quocdat.assignment_mob201.models.Enroll;

public class EnrollDAO implements IEnroll{

    AsmDB db;
    public EnrollDAO(Context context){
        db = AsmDB.getDbInstance(context);
    }
    @Override
    public boolean register(Enroll enroll) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        long rows = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("DATE", enroll.getDate());
            values.put("COURSE_ID", enroll.getCourse_id());
            values.put("USER_ID", enroll.getUser_id());
            rows = database.insertOrThrow("ENROLLS", null, values);

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
    public boolean leave(Enroll enroll) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        long rows = 0;
        try {
            rows = database.delete("ENROLLS","USER_ID = ? AND COURSE_ID = ?", new String[]
                    {String.valueOf(enroll.getUser_id()), String.valueOf(enroll.getCourse_id())});
            //Transaction: kiem tra neu insert bi loi se callback lai
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("leave: ", e.getMessage());
        }finally {
            database.endTransaction();
        }

        return rows == 1;
    }
}
