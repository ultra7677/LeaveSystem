package com.sapanywhere.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sapanywhere.app.entity.Company;
import com.sapanywhere.app.service.CompanyService;

@ControllerAdvice
public class CompanyControllerAdvice {
	
	@Autowired
	private CompanyService companyService;
	
	@ModelAttribute("companyInfo")
	public Company getCompanyInfo(){
		return this.companyService.findById((long) 1);
	}
	
}
