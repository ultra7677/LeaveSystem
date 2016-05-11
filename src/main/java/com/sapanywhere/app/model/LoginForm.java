package com.sapanywhere.app.model;

import javax.validation.constraints.NotNull;

public class LoginForm {

	@NotNull
	private String userEmail;
	
	@NotNull
	private String password;
	

	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
