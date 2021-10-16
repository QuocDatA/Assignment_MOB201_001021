package com.quocdat.assignment_mob201.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.quocdat.assignment_mob201.dao.EnrollDAO;
import com.quocdat.assignment_mob201.models.Enroll;

public class EnrolltService extends IntentService {

    public static final String EVENT_ENROLL_SERVICE = "EnrolltService";
    public static final String ACTION_REGISTER = "register";
    public static final String ACTION_LEAVE = "leave";

    private EnrollDAO dao;


    public EnrolltService() {
        super("EnrolltService");
        dao = new EnrollDAO(this);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Intent i = new Intent(EVENT_ENROLL_SERVICE);
            final String action = intent.getAction();
            Enroll enroll;
            boolean result;

            switch (action){
                case ACTION_REGISTER:
                    enroll = intent.getParcelableExtra("enroll");
                    result = dao.register(enroll);
                    i.putExtra("result", result);
                    break;
                case ACTION_LEAVE:
                    enroll = intent.getParcelableExtra("enroll");
                    result = dao.leave(enroll);
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