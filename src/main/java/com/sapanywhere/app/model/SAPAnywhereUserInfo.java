package com.sapanywhere.app.model;

public class SAPAnywhereUserInfo {
	private String audience;

	private String userName;

	private String userEmail;

	private String userPhone;

	private int tenantId;

	public String getAudience() {
		return audience;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setAudience(String audience) {
		this.audience = audience;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
}
