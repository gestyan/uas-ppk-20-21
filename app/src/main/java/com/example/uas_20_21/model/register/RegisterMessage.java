package com.example.uas_20_21.model.register;

import com.google.gson.annotations.SerializedName;

public class RegisterMessage {

	@SerializedName("status")
	private String status;

	@SerializedName("password")
	private String password;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	@SerializedName("confirm_password")
	private String confirmPassword;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setFullname(String fullname){
		this.fullname = fullname;
	}

	public String getFullname(){
		return fullname;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}

	public String getConfirmPassword(){
		return confirmPassword;
	}
}