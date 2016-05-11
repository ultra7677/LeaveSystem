package com.sapanywhere.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sapanywhere.app.service.user.UserInfo;

@ControllerAdvice
public class UserControllerAdvice {

	@ModelAttribute("currentUserInfo")
	public UserInfo getCurrentUserInfo(Authentication authentication){
		return (authentication == null) ? null : (UserInfo) authentication.getPrincipal();
	}

}
