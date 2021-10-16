package com.quocdat.assignment_mob201.activities;

import static com.quocdat.assignment_mob201.utilities.Constants.EXAM_TYPE;
import static com.quocdat.assignment_mob201.utilities.Constants.SCHEDULE_TYPE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quocdat.assignment_mob201.R;
import com.quocdat.assignment_mob201.adapter.ScheduleAdapter;
import com.quocdat.assignment_mob201.models.Course;
import com.quocdat.assignment_mob201.models.Enroll;
import com.quocdat.assignment_mob201.models.Schedule;
import com.quocdat.assignment_mob201.services.EnrolltService;
import com.quocdat.assignment_mob201.services.ScheduleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentCourseDetailActivity extends AppCompatActivity {

    TextView tvCourseDetailCode, tvCourseDetailTeacher, tvCourseDetailName;
    ListView lvSchedules, lvSchedulesExams;
    AppCompatButton btnJoined, btnCancel;

    List<Schedule> schedules, exams;
    ScheduleAdapter scheduleAdapter, examsAdapter;

    boolean joined = false;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_detail);
        tvCourseDetailCode = findViewById(R.id.tvCourseDetailCode);
        tvCourseDetailTeacher = findViewById(R.id.tvCourseDetailTeacher);
        tvCourseDetailName = findViewById(R.id.tvCourseDetailName);
        lvSchedules = findViewById(R.id.lvSchedules);
        lvSchedulesExams = findViewById(R.id.lvSchedulesExams);
        btnCancel = findViewById(R.id.btnCancel);
        btnJoined = findViewById(R.id.btnJoined);

        course = getIntent().getParcelableExtra("course");
        joined = getIntent().getBooleanExtra("joined", false);

        if (joined){
            btnJoined.setText("Rời khỏi");
        }else {
            btnJoined.setText("Tham gia");
        }

        tvCourseDetailCode.setText(course.getCode());
        tvCourseDetailTeacher.setText(course.getTeacher());
        tvCourseDetailName.setText(course.getName());

        schedules = new ArrayList<>();
        exams = new ArrayList<>();

        scheduleAdapter = new ScheduleAdapter(this, schedules);
        examsAdapter = new ScheduleAdapter(this, exams);

        lvSchedules.setAdapter(scheduleAdapter);
        lvSchedulesExams.setAdapter(examsAdapter);

        getSchedules(SCHEDULE_TYPE, course.getId());
        getSchedules(EXAM_TYPE, course.getId());
    }

    public void onButtonJoined(View view){
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
        int userId = preferences.getInt("id", 0);
        Intent intent = new Intent(this, EnrolltService.class);
        Enroll enroll = new Enroll(-1, course.getId(), userId, new Date().toString());
        if (joined){
            intent.setAction(EnrolltService.ACTION_LEAVE);
        }else {
            intent.setAction(EnrolltService.ACTION_REGISTER);
        }
        intent.putExtra("enroll",(Parcelable) enroll);
        startService(intent);
    }

    public void onButtonCancel(View view){
        finish();
    }

    public void getSchedules(int type, int courseId){
        Intent intent = new Intent(this, ScheduleService.class);
        intent.setAction(ScheduleService.ACTION_GET_ALL_SCHEDULE);
        intent.putExtra("type", type);
        intent.putExtra("courseId", courseId);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ScheduleService.EVENT_SCHEDULE_SERVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        IntentFilter filterEnroll = new IntentFilter(EnrolltService.EVENT_ENROLL_SERVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiverEnroll, filterEnroll);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiverEnroll);
    }

    private BroadcastReceiver receiverEnroll = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);

            if (resultCode == RESULT_OK) {
                String action = intent.getStringExtra("action");
                boolean result = intent.getBooleanExtra("result", false);
                switch (action){
                    case EnrolltService.ACTION_LEAVE:
                        if (result){
                            btnJoined.setText("Tham gia");
                        }else {
                            Toast.makeText(StudentCourseDetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case EnrolltService.ACTION_REGISTER:
                        if (result){
                            btnJoined.setText("Rời khỏi");
                        }else {
                            Toast.makeText(StudentCourseDetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default: break;
                }
            }
        }
    };

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String action = intent.getStringExtra("action");
                switch (action){
                    case ScheduleService.ACTION_GET_ALL_SCHEDULE:
                        Integer type = intent.getIntExtra("type", SCHEDULE_TYPE);
                        if (type == SCHEDULE_TYPE){
                            schedules.clear();
                            List<Schedule> _schedules = intent.getParcelableArrayListExtra("result");
                            schedules.addAll(_schedules);
                            scheduleAdapter.notifyDataSetChanged();
                        }else {
                            exams.clear();
                            List<Schedule> _exams = intent.getParcelableArrayListExtra("result");
                            exams.addAll(_exams);
                            examsAdapter.notifyDataSetChanged();
                        }
                        break;
                    default: break;
                }
            }
        }
    };
}