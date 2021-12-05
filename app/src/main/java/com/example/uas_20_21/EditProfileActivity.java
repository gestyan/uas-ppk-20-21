package com.example.uas_20_21;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uas_20_21.api.ApiClient;
import com.example.uas_20_21.api.ApiInterface;
import com.example.uas_20_21.model.login.LoginData;
import com.example.uas_20_21.model.update.Update;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    EditText fNameEditEt, emailEditEt, passwordEditEt, confirmPasswordEditEt;
    TextView usernameTv,alertPasswordTv, alertConfirmPasswordTv;
    Button editBtn, cancelEditBtn;
    SessionManager sessionManager;
    String fname, username, email, password;
    Integer userId;

    ApiInterface apiInterface;

    String pattern = "/^(?=.*[a-z])(?=.*[0-9])/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sessionManager = new SessionManager(EditProfileActivity.this);

        fNameEditEt = findViewById(R.id.fullname_update_et);
        usernameTv = findViewById(R.id.username_update_tv);
        emailEditEt = findViewById(R.id.email_update_et);
        passwordEditEt = findViewById(R.id.password_update_et);
        confirmPasswordEditEt = findViewById(R.id.confirm_password_update_et);

        userId = Integer.parseInt(sessionManager.getUserDetail().get(SessionManager.ID));
        fname = sessionManager.getUserDetail().get(SessionManager.FULLNAME);
        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL);

        fNameEditEt.setText(fname);
        usernameTv.setText(username);
        emailEditEt.setText(email);

        alertPasswordTv = findViewById(R.id.alert_edit_password_tv);
        alertPasswordTv.setVisibility(View.INVISIBLE);

        alertConfirmPasswordTv = findViewById(R.id.alert_edit_confirm_password_tv);
        alertConfirmPasswordTv.setVisibility(View.INVISIBLE);

        editBtn = findViewById(R.id.update_profile_btn);
        editBtn.setOnClickListener(this);

        cancelEditBtn = findViewById(R.id.cancel_update_profile_btn);
        cancelEditBtn.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update_profile_btn:
                alertPasswordTv.setVisibility(View.INVISIBLE);
                alertConfirmPasswordTv.setVisibility(View.INVISIBLE);

                if(fNameEditEt.getText().toString().equals("")){
                    fNameEditEt.setText(fname);
                }

                if(emailEditEt.getText().toString().equals("")){
                    emailEditEt.setText(email);
                }

                if(passwordEditEt.getText().toString().equals(confirmPasswordEditEt.getText().toString())){
                    if(passwordEditEt.getText().toString().equals("")){
                        fname = fNameEditEt.getText().toString();
                        email = emailEditEt.getText().toString();
                        password = passwordEditEt.getText().toString();
                        updateProfile(fname, email, password);
                    }else if(!Pattern.matches(passwordEditEt.getText().toString(),pattern)){
                        alertPasswordTv.setVisibility(View.VISIBLE);
                    }else{
                        updateProfile(fname, email, password);
                    }
                }else{
                    if(!Pattern.matches(passwordEditEt.getText().toString(),pattern)){
                        alertPasswordTv.setVisibility(View.VISIBLE);
                    }
                    alertConfirmPasswordTv.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.cancel_update_profile_btn:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        }
    }

    private void updateProfile(String fname, String email, String password) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Update> call = apiInterface.updateResponse(userId, fname, email, password);

        call.enqueue(new Callback<Update>() {
            @Override
            public void onResponse(Call<Update> call, Response<Update> response) {
                if((response.body() != null) && response.isSuccessful() && response.body().getStatus().equals("true")){
                    //Buat dapetin data baru
                    LoginData loginData =  new LoginData();
                    response.body().getData().getId();
                    loginData.setId(response.body().getData().getId());
                    loginData.setEmail(response.body().getData().getEmail());
                    loginData.setFullname(response.body().getData().getFullname());
                    loginData.setUsername(response.body().getData().getUsername());
                    sessionManager.createLoginSession(loginData);

                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Update> call, Throwable t) {

            }
        });
    }
}