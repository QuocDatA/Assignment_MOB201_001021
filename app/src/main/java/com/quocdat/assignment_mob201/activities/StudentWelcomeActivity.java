package com.quocdat.assignment_mob201.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;

import com.quocdat.assignment_mob201.R;
import com.quocdat.assignment_mob201.adapter.CourseAdapter;
import com.quocdat.assignment_mob201.models.Course;
import com.quocdat.assignment_mob201.models.User;
import com.quocdat.assignment_mob201.services.CourseService;

import java.util.ArrayList;
import java.util.List;

public class StudentWelcomeActivity extends AppCompatActivity {

    ListView lvJoinedCourses, lvAllCourses;
    CourseAdapter joinedCoursesAdapter, allCoursesAdapter;
    List<Course> allCourses, joinedCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_welcome);

        allCourses = new ArrayList<>();
        joinedCourses = new ArrayList<>();

        lvAllCourses = findViewById(R.id.lvCourses);
        lvJoinedCourses = findViewById(R.id.lvJoinedCourses);

        allCoursesAdapter = new CourseAdapter(this, allCourses, false);
        joinedCoursesAdapter = new CourseAdapter(this, joinedCourses, true);

        lvAllCourses.setAdapter(allCoursesAdapter);
        lvJoinedCourses.setAdapter(joinedCoursesAdapter);

        User user = getIntent().getParcelableExtra("user");

        getCourses(user.getId());
        getJoinedCourses(user.getId());
    }

    public void getCourses(int _studentId){
        Intent intent = new Intent(this, CourseService.class);
        intent.setAction(CourseService.ACTION_GET_ALL_COURSE_BY_STUDENT_ID);
        intent.putExtra("studentId", _studentId);
        startService(intent);
    }

    public void getJoinedCourses(int studentId){
        Intent intent = new Intent(this, CourseService.class);
        intent.setAction(CourseService.ACTION_GET_ALL_JOINED_COURSE_BY_STUDENT_ID);
        intent.putExtra("studentId", studentId);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(CourseService.EVENT_COURSE_SERVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        User user = getIntent().getParcelableExtra("user");
        getCourses(user.getId());
        getJoinedCourses(user.getId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String action = intent.getStringExtra("action");
                switch (action){
                    case CourseService.ACTION_GET_ALL_COURSE_BY_STUDENT_ID:
                        allCourses.clear();
                        List<Course> _allCourses = intent.getParcelableArrayListExtra("result");
                        allCourses.addAll(_allCourses);
                        allCoursesAdapter.notifyDataSetChanged();
                        break;
                    case CourseService.ACTION_GET_ALL_JOINED_COURSE_BY_STUDENT_ID:
                        joinedCourses.clear();
                        List<Course> _joinedCourses = intent.getParcelableArrayListExtra("result");
                        joinedCourses.addAll(_joinedCourses);
                        joinedCoursesAdapter.notifyDataSetChanged();
                        break;
                    default: break;
                }
            }
        }
    };
}