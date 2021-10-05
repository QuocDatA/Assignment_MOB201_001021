package com.quocdat.assignment_mob201.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.quocdat.assignment_mob201.dao.EnrollDAO;
import com.quocdat.assignment_mob201.models.Enroll;

public class EnrolltService extends IntentService {

    public static final String EVENT_ENROLL_SERVICE = "EnrolltService";
    public static final String ACTION_REGISTER = "register";

    private EnrollDAO dao;


    public EnrolltService() {
        super("EnrolltService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Intent i = new Intent(EVENT_ENROLL_SERVICE);
            final String action = intent.getAction();

            if (action == ACTION_REGISTER){
                Enroll enroll = intent.getParcelableExtra("enroll");
                boolean result = dao.register(enroll);
                i.putExtra("result", result);
                i.putExtra("resultCode", Activity.RESULT_OK);
                LocalBroadcastManager.getInstance(this).sendBroadcast(i);
                return;
            }

        }
    }

}