package com.quocdat.assignment_mob201.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.quocdat.assignment_mob201.database.AsmDB;
import com.quocdat.assignment_mob201.models.Course;
import com.quocdat.assignment_mob201.models.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO implements ISchedule{

    AsmDB db;
    public ScheduleDAO(Context context){
        db = AsmDB.getDbInstance(context);
    }
    @Override
    public List<Schedule> getAllSchedule(int type) {
        List<Schedule> list = new ArrayList<>();
        String q = "SELECT ID, DATE, TIME, ADDRESS, MEET, TYPE FROM SCHEDULES";
        String[] params = new String[]{String.valueOf(type)};
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(q, params);
        try{
            if (cursor.moveToFirst()){
                while (cursor.isAfterLast()){
                    Integer schedule_id = cursor.getInt(cursor.getColumnIndex("ID"));
                    Integer course_id = cursor.getInt(cursor.getColumnIndex("COURSE_ID"));
                    Integer type1 = cursor.getInt(cursor.getColumnIndex("TYPE"));
                    String date = cursor.getString(cursor.getColumnIndex("DATE"));
                    String time = cursor.getString(cursor.getColumnIndex("TIME"));
                    String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
                    String meet = cursor.getString(cursor.getColumnIndex("MEET"));


                    Schedule schedule = new Schedule(schedule_id, course_id, type1, date, time, address, meet);
                    list.add(schedule);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.d("getAllSchedule:  ", e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }

    @Override
    public boolean insert(Schedule schedule) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        long rows = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("COURSE_ID", schedule.getCourse_id());
            values.put("TYPE", schedule.getType());
            values.put("DATE", schedule.getDate());
            values.put("TIME", schedule.getTime());
            values.put("ADDRESS", schedule.getAddress());
            values.put("MEET", schedule.getMeet());
            rows = database.insertOrThrow("SCHEDULES", null, values);

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
    public boolean update(Schedule schedule) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        long rows = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("COURSE_ID", schedule.getCourse_id());
            values.put("TYPE", schedule.getType());
            values.put("DATE", schedule.getDate());
            values.put("TIME", schedule.getTime());
            values.put("ADDRESS", schedule.getAddress());
            values.put("MEET", schedule.getMeet());
            rows = database.update("SCHEDULES", values, "ID =?",
                    new String[]{String.valueOf(schedule.getId_schedule())});

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
    public boolean delete(int id) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        long rows = 0;
        try {
            rows = database.delete("SCHEDULES","ID = ?", new String[]
                    {String.valueOf(id)});
            //Transaction: kiem tra neu insert bi loi se callback lai
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.d("delete: ", e.getMessage());
        }finally {
            database.endTransaction();
        }

        return rows == 1;
    }
}
