package com.quocdat.assignment_mob201.activities;

import static com.quocdat.assignment_mob201.utilities.Constants.EXAM_TYPE;
import static com.quocdat.assignment_mob201.utilities.Constants.SCHEDULE_TYPE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quocdat.assignment_mob201.R;
import com.quocdat.assignment_mob201.adapter.ScheduleAdapter;
import com.quocdat.assignment_mob201.models.Course;
import com.quocdat.assignment_mob201.models.Schedule;
import com.quocdat.assignment_mob201.services.CourseService;
import com.quocdat.assignment_mob201.services.EnrolltService;
import com.quocdat.assignment_mob201.services.ScheduleService;

import java.util.ArrayList;
import java.util.List;

public class AdminCourseDetailActivity extends AppCompatActivity {

    TextView tvCourseDetailCode, tvCourseDetailTeacher, tvCourseDetailName;
    ListView lvSchedules, lvSchedulesExams;

    List<Schedule> schedules, exams;
    ScheduleAdapter scheduleAdapter, examsAdapter;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_detail);


        tvCourseDetailCode = findViewById(R.id.tvCourseDetailCode);
        tvCourseDetailTeacher = findViewById(R.id.tvCourseDetailTeacher);
        tvCourseDetailName = findViewById(R.id.tvCourseDetailName);
        lvSchedules = findViewById(R.id.lvSchedules);
        lvSchedulesExams = findViewById(R.id.lvSchedulesExams);

        course = getIntent().getParcelableExtra("course");

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

    public void onEdit(View view){
        Intent intent = new Intent(this, CourseService.class);
        intent.putExtra("course", course);
        startService(intent);

        openDialogEdit();
    }

    public void onRemove(View view){
        Intent intent = new Intent(this, CourseService.class);
        intent.setAction(CourseService.ACTION_DELETE);
        intent.putExtra("course", course);
        startService(intent);
    }

    public void openDialogEdit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminCourseDetailActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_course, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtCoureNameEdit = view.findViewById(R.id.edtCoureNameEdit);
        EditText edtCourseCodeEdit = view.findViewById(R.id.edtCourseCodeEdit);
        EditText edtCourseTeacherEdit = view.findViewById(R.id.edtCourseTeacherEdit);
        Button btnSaveEdit = view.findViewById(R.id.btnSaveEdit);
        Button btnCancelEdit = view.findViewById(R.id.btnCancelEdit);

        course = getIntent().getParcelableExtra("course");

        edtCoureNameEdit.setText(course.getName());
        edtCourseCodeEdit.setText(course.getCode());
        edtCourseTeacherEdit.setText(course.getTeacher());

        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edtCourseCodeEdit.getText().toString();
                String name = edtCoureNameEdit.getText().toString();
                String teacher = edtCourseTeacherEdit.getText().toString();

                course.setCode(code);
                course.setName(name);
                course.setTeacher(teacher);

                Intent intent = new Intent(AdminCourseDetailActivity.this, CourseService.class);
                intent.setAction(CourseService.ACTION_UPDATE);
                intent.putExtra("course", course);
                startService(intent);
            }
        });


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

        IntentFilter courseServiceFilter = new IntentFilter(CourseService.EVENT_COURSE_SERVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(CourseServicereceiver, courseServiceFilter);

        course = getIntent().getParcelableExtra("course");
        getSchedules(SCHEDULE_TYPE, course.getId());
        getSchedules(EXAM_TYPE, course.getId());
    }

    @Override
    protected void onPause () {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

        LocalBroadcastManager.getInstance(this).unregisterReceiver(CourseServicereceiver);
    }

    private BroadcastReceiver CourseServicereceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String action = intent.getStringExtra("action");
                boolean result;
                switch (action){
                    case CourseService.ACTION_DELETE:
                        result = intent.getBooleanExtra("result", false);
                        if (result){
                            finish();
                        }else {
                            Toast.makeText(AdminCourseDetailActivity.this, "This course can not delete!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case CourseService.ACTION_UPDATE:
                        result = intent.getBooleanExtra("result", false);
                        if (result){
                            finish();
                        }else {
                            Toast.makeText(AdminCourseDetailActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
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