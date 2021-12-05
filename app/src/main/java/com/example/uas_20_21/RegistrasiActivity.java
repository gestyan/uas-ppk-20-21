package com.example.uas_20_21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uas_20_21.api.ApiClient;
import com.example.uas_20_21.api.ApiInterface;
import com.example.uas_20_21.model.register.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrasiActivity extends AppCompatActivity implements View.OnClickListener {

    EditText fNameEt, usernameEt, emailEt, passwordEt, confirmPasswordEt;
    Button submitBtn;
    TextView haveAnAccountTv;
    TextView alertUsernameTv, alertfNameTv, alertEmailTv, alertPasswordTv, alertConfirmPassowrdTv;
    String fName, username, email, password, confirmPassword;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        fNameEt = findViewById(R.id.fullname_registrasi_et);
        usernameEt = findViewById(R.id.username_registrasi_et);
        emailEt = findViewById(R.id.email_registrasi_et);
        passwordEt = findViewById(R.id.password_registrasi_et);
        confirmPasswordEt = findViewById(R.id.confirm_password_registrasi_et);

        alertfNameTv = findViewById(R.id.alert_fullname_tv);
        alertUsernameTv = findViewById(R.id.alert_username_tv);
        alertEmailTv = findViewById(R.id.alert_email_tv);
        alertPasswordTv = findViewById(R.id.alert_password_tv);
        alertConfirmPassowrdTv = findViewById(R.id.alert_confirm_password_tv);

        submitBtn = findViewById(R.id.submit_registrasi_btn);
        submitBtn.setOnClickListener(this);

        haveAnAccountTv = findViewById(R.id.already_have_account_tv);
        haveAnAccountTv.setOnClickListener(this);

        hideAllAlert();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_registrasi_btn:
                    fName = fNameEt.getText().toString();
                    username = usernameEt.getText().toString();
                    email = emailEt.getText().toString();
                    password = passwordEt.getText().toString();
                    confirmPassword = confirmPasswordEt.getText().toString();
                    registrasi(fName, username, email, password, confirmPassword);
                break;
            case R.id.already_have_account_tv:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void registrasi(String fName, String username, String email, String password, String confirmPassword) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register> call = apiInterface.registerResponse(username, fName, email, password, confirmPassword);

        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if((response.body() != null) && response.isSuccessful() && response.body().getStatus().equals("true")){
                    Intent intent = new Intent(RegistrasiActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    hideAllAlert();
                    if(!response.body().getMessage().getFullname().toString().equals("true")){
                        alertfNameTv.setText(response.body().getMessage().getFullname().toString());
                        alertfNameTv.setVisibility(View.VISIBLE);
                    }

                    if(!response.body().getMessage().getUsername().toString().equals("true")){
                        alertUsernameTv.setText(response.body().getMessage().getUsername().toString());
                        alertUsernameTv.setVisibility(View.VISIBLE);
                    }

                    if(!response.body().getMessage().getEmail().toString().equals("true")){
                        alertEmailTv.setText(response.body().getMessage().getEmail().toString());
                        alertEmailTv.setVisibility(View.VISIBLE);
                    }

                    if(!response.body().getMessage().getPassword().toString().equals("true")){
                        alertPasswordTv.setText(response.body().getMessage().getPassword().toString());
                        alertPasswordTv.setVisibility(View.VISIBLE);
                    }

                    if(!response.body().getMessage().getConfirmPassword().toString().equals("true")){
                        alertConfirmPassowrdTv.setText(response.body().getMessage().getConfirmPassword().toString());
                        alertConfirmPassowrdTv.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

            }
        });


    }

    private void hideAllAlert(){
        alertfNameTv.setVisibility(View.INVISIBLE);
        alertUsernameTv.setVisibility(View.INVISIBLE);
        alertEmailTv.setVisibility(View.INVISIBLE);
        alertPasswordTv.setVisibility(View.INVISIBLE);
        alertConfirmPassowrdTv.setVisibility(View.INVISIBLE);
    }
}