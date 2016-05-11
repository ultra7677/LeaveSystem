package com.sapanywhere.app.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sapanywhere.app.model.LoginForm;
import com.sapanywhere.app.model.RegisterForm;
import com.sapanywhere.app.service.UserService;

@Controller
//@SessionAttributes("user")
public class UserController {

	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String loadLoginPage(LoginForm loginForm) {
		return "/accounts/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loadLoginPage2(LoginForm loginForm) {
		return "/accounts/login";
	}

	@RequestMapping(value = "/register.html", method = RequestMethod.GET)
	public String loadRegisterPage(RegisterForm registerForm) {
		return "/accounts/register";
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public String register(@Valid RegisterForm registerForm,
			BindingResult result) {
		registerForm.onValid(result);
		
		if (result.hasErrors()) {
			return "/accounts/register";
		}

		try {
			this.userService.create(registerForm.parse());
		} catch (Exception ex) {
			logger.error(ex);
			if (ex.getCause() != null
					&& ex.getCause().getClass() == ConstraintViolationException.class) {
				FieldError error = new FieldError(RegisterForm.FORMNAME, "email",
						null, false,
						new String[] { "register.error.emailexist" }, null,
						null);
				result.addError(error);
			} else {
				result.reject("register.error.failed");
			}

			return "/accounts/register";
		}

		return "redirect:/login.html";
	}
}
