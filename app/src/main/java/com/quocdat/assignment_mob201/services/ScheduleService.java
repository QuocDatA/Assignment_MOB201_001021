package com.quocdat.assignment_mob201.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.quocdat.assignment_mob201.dao.ScheduleDAO;
import com.quocdat.assignment_mob201.models.Course;
import com.quocdat.assignment_mob201.models.Schedule;

import java.util.ArrayList;
import java.util.List;


public class ScheduleService extends IntentService {

    public static final String EVENT_SCHEDULE_SERVICE = "ScheduleService";
    public static final String ACTION_GET_ALL_SCHEDULE = "getAllSchedule";
    public static final String ACTION_INSERT = "insert";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_GET = "get";

    private ScheduleDAO dao;

    public ScheduleService() {
        super("ScheduleService");
        dao = new ScheduleDAO(this);
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Intent i = new Intent(EVENT_SCHEDULE_SERVICE);
            final String action = intent.getAction();
            boolean result = false;
            Schedule schedule = null;

            switch (action){
                case ACTION_GET_ALL_SCHEDULE:
                    Integer courseId = intent.getIntExtra("courseId", 0);
                    Integer type = intent.getIntExtra("type", 0);
                    List<Schedule> allSchedule = dao.getAllSchedule(type, courseId);
                    i.putExtra("type", type);
                    i.putParcelableArrayListExtra("result", (ArrayList<Schedule>) allSchedule);
                    break;
                case ACTION_INSERT:
                    schedule = intent.getParcelableExtra("schedule");
                    result = dao.insert(schedule);
                    i.putExtra("result", result);
                    break;
                case ACTION_UPDATE:
                    schedule = intent.getParcelableExtra("schedule");
                    result = dao.update(schedule);
                    i.putExtra("result", result);
                    break;
                case ACTION_DELETE:
                    schedule = intent.getParcelableExtra("schedule");
                    result = dao.delete(schedule.getId());
                    i.putExtra("result", result);
                    break;
                case ACTION_GET:
                    Integer _type = intent.getIntExtra("type", 0);
                    List<Schedule> all = dao.get(_type);
                    i.putParcelableArrayListExtra("result", (ArrayList<Schedule>) all);
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