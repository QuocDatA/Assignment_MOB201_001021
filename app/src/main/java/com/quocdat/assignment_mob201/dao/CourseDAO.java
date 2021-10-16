package com.quocdat.assignment_mob201.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.quocdat.assignment_mob201.database.AsmDB;
import com.quocdat.assignment_mob201.models.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseDAO implements ICourse{

    AsmDB db;
    public CourseDAO(Context context){
        db = AsmDB.getDbInstance(context);
    }

    @Override
    public List<Course> getAllCourses() {
        String q = "SELECT ID, NAME, CODE, TEACHER FROM COURSES";
        return getCourse(q, null, "getAllCourses");
    }

    //select * from courses inner join enroll on courseid = courseid where userid =2
    @Override
    public List<Course> getJoinedCourses(int studentId) {
        String q = "SELECT C.ID, C.NAME, C.CODE, C.TEACHER FROM COURSES C INNER JOIN ENROLLS E ON E.COURSE_ID = C.ID WHERE E.USER_ID = ?";
        String[] params = new String[]{String.valueOf(studentId)};
        return getCourse(q, params, "getJoinedCourses");
    }

    @Override
    public List<Course> getNotJoinedCourses(int studentId) {
        String q = "select cs.ID, cs.CODE, cs.NAME, cs.TEACHER from courses cs where id not in ( select c.id from courses c inner join enrolls e on e.COURSE_ID = c.ID where e.USER_ID = ?)";
        String[] params = new String[]{String.valueOf(studentId)};
        return getCourse(q, params, "getNotJoinedCourses");
    }

    @Override
    public Course getCourseById(int id) {
        String q = "SELECT ID, NAME, CODE, TEACHER FROM COURSES WHERE ID = ?";
        String[] params = new String[]{String.valueOf(id)};
        List<Course> list = getCourse(q, params, "getCourseById");
        if (list.size()>0) return list.get(0);
        return null;
    }

    @Override
    public boolean insert(Course course) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        long rows = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("NAME", course.getName());
            values.put("CODE", course.getCode());
            values.put("TEACHER", course.getTeacher());
            rows = database.insertOrThrow("COURSES", null, values);

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
    public boolean update(Course course) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.beginTransaction();
        long rows = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("NAME", course.getName());
            values.put("CODE", course.getCode());
            values.put("TEACHER", course.getTeacher());
            rows = database.update("COURSES", values, "ID = ?", new String[]
                    {String.valueOf(course.getId())});

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
            rows = database.delete("COURSES","ID = ?", new String[]
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

    private List<Course> getCourse(String q, String[] params, String ex){
        List<Course> list = new ArrayList<>();
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(q, params);
        try{
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Integer id = cursor.getInt(cursor.getColumnIndex("ID"));
                    String name = cursor.getString(cursor.getColumnIndex("NAME"));
                    String code = cursor.getString(cursor.getColumnIndex("CODE"));
                    String teacher = cursor.getString(cursor.getColumnIndex("TEACHER"));

                    Course course = new Course(id, name, code, teacher);
                    list.add(course);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.d(ex, e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }
}
