package com.quocdat.assignment_mob201.activities;

import static com.quocdat.assignment_mob201.utilities.Constants.ROLE_ADMIN;
import static com.quocdat.assignment_mob201.utilities.Constants.ROLE_STUDENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.quocdat.assignment_mob201.R;
import com.quocdat.assignment_mob201.models.User;
import com.quocdat.assignment_mob201.services.UserService;

public class StudentSignUpActivity extends AppCompatActivity {

    private TextInputEditText edtFullName, edtUserName, edtPassword, edtPhone, edtDOB;
    AppCompatButton btnCancelSiguUp, btnSaveSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        edtFullName = findViewById(R.id.edtFullNameSignUp);
        edtUserName = findViewById(R.id.edtUserNameSignUp);
        edtPassword = findViewById(R.id.edtPasswordSignUp);
        edtPhone = findViewById(R.id.edtPhoneSignUp);
        edtDOB = findViewById(R.id.edtDayOfBirthSignUp);
        btnSaveSignUp = findViewById(R.id.btnSaveSignUp);
        btnCancelSiguUp = findViewById(R.id.btnCancelSignUp);
    }

    public void onCancelClick(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSaveClick(View view){
        String name = edtFullName.getText().toString();
        String username = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();
        String phone = edtPhone.getText().toString();
        String dob = edtDOB.getText().toString();

        User user = new User(-1, ROLE_STUDENT, username, password, name, phone, dob);

        Intent intent = new Intent(this, UserService.class);
        intent.setAction(UserService.ACTION_REGISTER);
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
                boolean result = intent.getBooleanExtra("result", false);
                switch (action){
                    case UserService.ACTION_REGISTER:
                        if (result == false){
                            Toast.makeText(StudentSignUpActivity.this, "UserName Existed!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(StudentSignUpActivity.this, "Successfully!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(StudentSignUpActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                        break;
                    default: break;
                }
            }
        }
    };
}