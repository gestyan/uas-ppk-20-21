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
import com.example.uas_20_21.model.login.Login;
import com.example.uas_20_21.model.login.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText usernameEt, passwordEt;
    Button loginBtn;
    TextView newAccountTv;
    String username, password;

    ApiInterface apiInterface;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(LoginActivity.this);

        if(sessionManager.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        usernameEt = findViewById(R.id.username_et);
        passwordEt = findViewById(R.id.password_et);

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);

        newAccountTv = findViewById(R.id.new_account_tv);
        newAccountTv.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                username = usernameEt.getText().toString();
                password = passwordEt.getText().toString();
                login(username, password);
                break;
            case R.id.new_account_tv:
                Intent intent = new Intent(this, RegistrasiActivity.class);
                startActivity(intent);
        }
    }

    private void login(String username, String password) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Login> loginCall = apiInterface.loginResponse(username, password);
        
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if((response.body() != null) && response.isSuccessful()){
                    if(response.body().getStatus().equals("true")){
                        sessionManager = new SessionManager(LoginActivity.this);
                        LoginData loginData = response.body().getData();
                        sessionManager.createLoginSession(loginData);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
//                        Toast.makeText(LoginActivity.this, response.body().getStatus().toString(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}