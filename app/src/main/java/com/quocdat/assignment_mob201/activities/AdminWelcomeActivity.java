package com.quocdat.assignment_mob201.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.quocdat.assignment_mob201.R;
import com.quocdat.assignment_mob201.adapter.CourseAdapter;
import com.quocdat.assignment_mob201.models.Course;
import com.quocdat.assignment_mob201.models.User;
import com.quocdat.assignment_mob201.services.CourseService;

import java.util.ArrayList;
import java.util.List;

public class AdminWelcomeActivity extends AppCompatActivity {

    ListView lvAllCourses;
    CourseAdapter allCoursesAdapter;
    List<Course> allCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);

        lvAllCourses = findViewById(R.id.lvAllCourses);

        allCourses = new ArrayList<>();

        allCoursesAdapter = new CourseAdapter(this, allCourses, false, true);

        lvAllCourses.setAdapter(allCoursesAdapter);

        User user = getIntent().getParcelableExtra("user");

        getCourses();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_out_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out){
            Intent intent = new Intent(AdminWelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void getCourses(){
        Intent intent = new Intent(this, CourseService.class);
        intent.setAction(CourseService.ACTION_GET_ALL_COURSE);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(CourseService.EVENT_COURSE_SERVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        getCourses();
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
                    case CourseService.ACTION_GET_ALL_COURSE:
                        allCourses.clear();
                        List<Course> _allCourses = intent.getParcelableArrayListExtra("result");
                        allCourses.addAll(_allCourses);
                        allCoursesAdapter.notifyDataSetChanged();
                        break;
                    default: break;
                }
            }
        }
    };
}