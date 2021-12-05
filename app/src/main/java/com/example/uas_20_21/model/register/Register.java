package com.example.uas_20_21.model.register;

import java.util.List;

import com.example.uas_20_21.model.register.RegisterMessage;
import com.google.gson.annotations.SerializedName;

public class Register{

	@SerializedName("message")
	private RegisterMessage registerMessage;

	@SerializedName("status")
	private String status;

	public void setMessage(RegisterMessage registerMessage){
		this.registerMessage = registerMessage;
	}

	public RegisterMessage getMessage(){
		return registerMessage;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}