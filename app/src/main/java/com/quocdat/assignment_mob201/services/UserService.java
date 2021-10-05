package com.quocdat.assignment_mob201.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Parcelable;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.quocdat.assignment_mob201.dao.UserDAO;
import com.quocdat.assignment_mob201.models.Course;
import com.quocdat.assignment_mob201.models.User;

import java.util.List;


public class UserService extends IntentService {

    public static final String EVENT_USER_SERVICE = "UserService";
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_REGISTER = "register";
    public static final String ACTION_UPDATE = "update";

    private UserDAO dao;

    public UserService() {
        super("UserService");
        dao = new UserDAO(getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Intent i = new Intent(EVENT_USER_SERVICE);
            final String action = intent.getAction();
            boolean result = false;
            User user = null;

            switch (action){
                case ACTION_LOGIN:
                    String username = intent.getStringExtra("username");
                    String password = intent.getStringExtra("password");
                    result = dao.login(username, password);
                    i.putExtra("result", result);
                    break;
                case ACTION_REGISTER:
                    user = intent.getParcelableExtra("user");
                    result = dao.register(user);
                    i.putExtra("result", result);
                    break;
                case ACTION_UPDATE:
                    user = intent.getParcelableExtra("user");
                    result = dao.update(user);
                    i.putExtra("result", result);
                    break;
            }
            i.putExtra("resultCode", Activity.RESULT_OK);
            LocalBroadcastManager.getInstance(this).sendBroadcast(i);

        }

    }
}