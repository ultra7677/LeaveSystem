package com.sapanywhere.app.exception;

public class BusinessException extends Exception {
	private static final long serialVersionUID = -4803343307522023150L;
	
	public BusinessException(String code){
		this.code = code;
	}
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
