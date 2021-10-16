package com.quocdat.assignment_mob201.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.quocdat.assignment_mob201.dao.CourseDAO;
import com.quocdat.assignment_mob201.models.Course;

import java.util.ArrayList;
import java.util.List;


public class CourseService extends IntentService {

    public static final String EVENT_COURSE_SERVICE = "CourseService";
    public static final String ACTION_GET_ALL_COURSE = "getAllCourse";
    public static final String ACTION_GET_ALL_JOINED_COURSE_BY_STUDENT_ID = "getJoinedCourses";
    public static final String ACTION_GET_ALL_COURSE_BY_STUDENT_ID = "getNotJoinedCourses";
    public static final String ACTION_GET_COURSE_BY_ID = "getCourseById";
    public static final String ACTION_INSERT = "insert";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_DELETE = "delete";

    private CourseDAO dao;


    public CourseService() {
        super("CourseService");
        dao = new CourseDAO(this);
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
                    i.putParcelableArrayListExtra("result", (ArrayList<Course>) allCourses);
                    break;
                case ACTION_GET_ALL_JOINED_COURSE_BY_STUDENT_ID:
                    Integer studentId = intent.getIntExtra("studentId", 0);
                    List<Course> allCoursesByStudentId = dao.getJoinedCourses(studentId);
                    i.putParcelableArrayListExtra("result", (ArrayList<Course>) allCoursesByStudentId);
                    break;
                case ACTION_GET_ALL_COURSE_BY_STUDENT_ID:
                    Integer _studentId = intent.getIntExtra("studentId", 0);
                    List<Course> _allCoursesByStudentId = dao.getNotJoinedCourses(_studentId);
                    i.putParcelableArrayListExtra("result", (ArrayList<Course>) _allCoursesByStudentId);
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
                default: break;
            }
            i.putExtra("action", action);
            i.putExtra("resultCode", Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}