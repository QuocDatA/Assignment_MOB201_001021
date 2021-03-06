package com.quocdat.assignment_mob201.activities;

import static com.quocdat.assignment_mob201.utilities.Constants.ROLE_ADMIN;
import static com.quocdat.assignment_mob201.utilities.Constants.ROLE_STUDENT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.quocdat.assignment_mob201.R;
import com.quocdat.assignment_mob201.models.User;
import com.quocdat.assignment_mob201.services.UserService;

import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edtUsername, edtPassword;
    private TextView tvSignup;

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        readLoginStatus();

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        tvSignup = findViewById(R.id.tvSignup);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.btnLoginGoogle);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 1000);
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, StudentSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo("com.demo.demo001", PackageManager.GET_SIGNATURES);
            for(Signature signature: packageInfo.signatures){
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.d("KeyHash:>>>>>>>>>>>>>>>>>", Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));
            }
        }catch (Exception e){
            
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("BBB", "onActivityResult: " + account.getEmail());
            }catch (Exception e){
                Log.d("BBB", "onActivityResult: " + e.getMessage());
            }
        }
    }

    public void onLogin(View view){
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        User user = new User(-1, -1, username, password, "", "", "");


        Intent intent = new Intent(this, UserService.class);
        intent.setAction(UserService.ACTION_LOGIN);
        intent.putExtra("user", (Parcelable) user);

        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(UserService.EVENT_USER_SERVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(loginReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(loginReceiver);
    }

    private BroadcastReceiver loginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);
            if (resultCode == RESULT_OK) {
                String action = intent.getStringExtra("action");
                switch (action){
                    case UserService.ACTION_LOGIN:
                        User user = intent.getParcelableExtra("result");
                        if (user == null){
                            Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (user.getRole() == ROLE_ADMIN){
                            startIntentActivity(ROLE_ADMIN, user);
                        }else {
                            startIntentActivity(ROLE_STUDENT, user);
                        }
                        break;
                    default: break;
                }
            }
        }
    };

    public void startIntentActivity(int type, User user){
        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (type == ROLE_ADMIN){
            i.setClass(LoginActivity.this, AdminWelcomeActivity.class);
        }else {
            i.setClass(LoginActivity.this, StudentWelcomeActivity.class);
        }
        i.putExtra("user",(Parcelable) user);
        writeLoginStatus(user);

        startActivity(i);
        finish();
    }

    private void writeLoginStatus(User user){
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("remember", true);
        editor.putInt("id", user.getId());
        editor.putInt("role", user.getRole());
        editor.putString("name", user.getName());
        editor.putString("username", user.getUsername());
        editor.commit();
    }

    private void readLoginStatus(){
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
        boolean remember = preferences.getBoolean("remember", false);
        if (remember){
            int id = preferences.getInt("id", -1);
            int role = preferences.getInt("role", -1);
            String name = preferences.getString("name", null);
            String username = preferences.getString("username", null);

            User user = new User(id, role, username, null, name, null, null);
            startIntentActivity(role, user);
        }
    }
}