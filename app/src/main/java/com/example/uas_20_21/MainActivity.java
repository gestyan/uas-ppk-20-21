package com.example.uas_20_21;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView fnameTv;
    Button editBtn, logOutBtn;
    SessionManager sessionManager;
    String fname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(MainActivity.this);

        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        fnameTv = findViewById(R.id.userfnameInfo_tv);

        editBtn = findViewById(R.id.editProfil_btn);
        editBtn.setOnClickListener(this);

        logOutBtn = findViewById(R.id.logout_btn);
        logOutBtn.setOnClickListener(this);

        fname = sessionManager.getUserDetail().get(SessionManager.FULLNAME);

        fnameTv.setText(fname);
    }

    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editProfil_btn:
                editProfile();
                break;

            case R.id.logout_btn:
                sessionManager.logoutSession();
                moveToLogin();
        }
    }

    private void editProfile() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
}