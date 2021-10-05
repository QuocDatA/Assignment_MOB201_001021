package com.quocdat.assignment_mob201.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Parcelable;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.quocdat.assignment_mob201.dao.CourseDAO;
import com.quocdat.assignment_mob201.models.Course;

import java.util.List;
import java.util.Locale;


public class CourseService extends IntentService {

    public static final String EVENT_COURSE_SERVICE = "CourseService";
    public static final String ACTION_GET_ALL_COURSE = "getAllCourse";
    public static final String ACTION_GET_ALL_COURSE_BY_STUDENT_ID = "getAllCoursesByStudentId";
    public static final String ACTION_GET_COURSE_BY_ID = "getCourseById";
    public static final String ACTION_INSERT = "insert";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_DELETE = "delete";

    private CourseDAO dao;


    public CourseService() {
        super("CourseService");
        dao = new CourseDAO(getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Intent i = new Intent(EVENT_COURSE_SERVICE);
            final String action = intent.getAction();
            boolean result = false;
            Course course = null;
            switch (action){
                case ACTION_GET_ALL_COURSE:
                    List<Course> allCourses = dao.getAllCourses();
                    i.putExtra("result", (Parcelable) allCourses);
                    break;
                case ACTION_GET_ALL_COURSE_BY_STUDENT_ID:
                    Integer studentId = intent.getIntExtra("studentId", 0);
                    List<Course> allCoursesByStudentId = dao.getAllCoursesByStudentId(studentId);
                    i.putExtra("result", (Parcelable) allCoursesByStudentId);
                    break;
                case ACTION_GET_COURSE_BY_ID:
                    Integer courseId = intent.getIntExtra("courseId", 0);
                    Course courseById = dao.getCourseById(courseId);
                    i.putExtra("result", (Parcelable) courseById);
                    break;
                case ACTION_INSERT:
                    course = intent.getParcelableExtra("course");
                    result = dao.insert(course);
                    i.putExtra("result", result);
                    break;
                case ACTION_UPDATE:
                    course = intent.getParcelableExtra("course");
                    result = dao.update(course);
                    i.putExtra("result", result);
                    break;
                case ACTION_DELETE:
                    course = intent.getParcelableExtra("course");
                    result = dao.delete(course.getId());
                    i.putExtra("result", result);
                    break;
            }
            i.putExtra("resultCode", Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);
        }
    }


}