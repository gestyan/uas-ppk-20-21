package com.example.uas_20_21.model.login;

import com.google.gson.annotations.SerializedName;

public class Login{

	@SerializedName("data")
	private LoginData loginData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setData(LoginData loginData){
		this.loginData = loginData;
	}

	public LoginData getData(){
		return loginData;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}