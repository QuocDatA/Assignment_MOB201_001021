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
    public List<Schedule> getAllSchedule(int type, int courseId) {
        List<Schedule> list = new ArrayList<>();
        String q = "SELECT ID, DATE, TIME, ADDRESS, MEET, COURSE_ID FROM SCHEDULES WHERE TYPE = ? AND COURSE_ID = ?";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(q, new String[]{String.valueOf(type), String.valueOf(courseId)});
        try{
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Integer schedule_id = cursor.getInt(cursor.getColumnIndex("ID"));
                    Integer course_id = cursor.getInt(cursor.getColumnIndex("COURSE_ID"));
                    String date = cursor.getString(cursor.getColumnIndex("DATE"));
                    String time = cursor.getString(cursor.getColumnIndex("TIME"));
                    String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
                    String meet = cursor.getString(cursor.getColumnIndex("MEET"));

                    Schedule schedule = new Schedule();
                    schedule.setId(schedule_id);
                    schedule.setCourse_id(course_id);
                    schedule.setDate(date);
                    schedule.setTime(time);
                    schedule.setAddress(address);
                    schedule.setMeet(meet);
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
    public List<Schedule> get(int type) {
        List<Schedule> list = new ArrayList<>();
        String q = "SELECT ID, DATE, TIME, ADDRESS, MEET FROM SCHEDULES WHERE TYPE = ?";
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(q, new String[]{String.valueOf(type)});
        try{
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Integer schedule_id = cursor.getInt(cursor.getColumnIndex("ID"));
                    Integer course_id = cursor.getInt(cursor.getColumnIndex("COURSE_ID"));
                    String date = cursor.getString(cursor.getColumnIndex("DATE"));
                    String time = cursor.getString(cursor.getColumnIndex("TIME"));
                    String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
                    String meet = cursor.getString(cursor.getColumnIndex("MEET"));

                    Schedule schedule = new Schedule();
                    schedule.setId(schedule_id);
                    schedule.setCourse_id(course_id);
                    schedule.setDate(date);
                    schedule.setTime(time);
                    schedule.setAddress(address);
                    schedule.setMeet(meet);
                    list.add(schedule);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.d("get:  ", e.getMessage());
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

        return rows >= 1;
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
                    new String[]{String.valueOf(schedule.getId())});

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
