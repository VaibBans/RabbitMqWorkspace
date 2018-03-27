package com.example.demo;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class UserBean {

	private String userName;
	private String password;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}