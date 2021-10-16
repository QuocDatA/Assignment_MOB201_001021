package com.quocdat.assignment_mob201.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Parcelable;

import androidx.annotation.Nullable;
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
    public static final String ACTION_CHECK_USERNAME_EXIST = "checkUsernameExist";

    private UserDAO dao;

    public UserService() {
        super("UserService");
        dao = new UserDAO(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Intent i = new Intent(EVENT_USER_SERVICE);
            final String action = intent.getAction();
            boolean result;
            User user;

            switch (action){
                case ACTION_LOGIN:
                    user = intent.getParcelableExtra("user");
                    user = dao.login(user.getUsername(), user.getPassword());
                    i.putExtra("result", (Parcelable) user);
                    break;
                case ACTION_REGISTER:
                    user = intent.getParcelableExtra("user");

                    boolean checkUsername = dao.checkUsernameExist(user.getUsername());
                    if (checkUsername){
                        i.putExtra("result", false);
                    }else {
                        result = dao.register(user);
                        i.putExtra("result", result);
                    }
                    break;
                case ACTION_UPDATE:
                    user = intent.getParcelableExtra("user");
                    result = dao.update(user);
                    i.putExtra("result", result);
                    break;
                case ACTION_CHECK_USERNAME_EXIST:
                    String username = intent.getStringExtra("username");
                    result = dao.checkUsernameExist(username);
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