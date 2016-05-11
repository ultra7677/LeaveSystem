package com.sapanywhere.app.service;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LocaleService {

	private static Logger logger = Logger.getLogger(LocaleService.class);
	
	@Autowired
	private ApplicationContext applicationContext; 
	
	public String getMessage(String code, Object[] args){
		Locale locale = LocaleContextHolder.getLocale();
		try{
			return applicationContext.getMessage(code,args, locale);
		}catch(Exception ex){
			logger.error("Failed to get message:" + code);
			logger.error(ex);
		}
		
		return null;
	}
	
	public String getMessage(String code,Object arg){
		return this.getMessage(code, new Object[] {arg});
	}
}
