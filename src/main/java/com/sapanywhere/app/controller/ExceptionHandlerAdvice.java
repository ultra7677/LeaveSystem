package com.sapanywhere.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Controller
@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String InternalServerError() {
		return "redirect:/500.html";
	}

	
	@RequestMapping(value = "/404.html", method = {RequestMethod.GET,RequestMethod.POST})
	public String load404Page() {
		return "/errors/404";
	}
	
	@RequestMapping(value = "/500.html", method = {RequestMethod.GET,RequestMethod.POST})
	public String load500Page() {
		return "/errors/500";
	}
}
