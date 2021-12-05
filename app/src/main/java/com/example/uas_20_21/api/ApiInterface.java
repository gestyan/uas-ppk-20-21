package com.example.uas_20_21.api;

import com.example.uas_20_21.model.login.Login;
import com.example.uas_20_21.model.register.Register;
import com.example.uas_20_21.model.update.Update;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login")
    Call<Login> loginResponse(
      @Field("username") String username,
      @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<Register> registerResponse(
            @Field("username") String username,
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password
    );

    @FormUrlEncoded
    @POST("update")
    Call<Update> updateResponse(
            @Field("id") Integer id,
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("password") String password
    );
}
