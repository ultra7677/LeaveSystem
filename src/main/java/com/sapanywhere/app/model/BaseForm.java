package com.sapanywhere.app.model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public abstract class BaseForm {

	public abstract void onValid(BindingResult result);

	protected void AddFieldError(BindingResult result,
			String formName,
			String fieldName, 
			String[] codes, 
			Object[] arguments) {
		FieldError error = new FieldError(formName, 
				fieldName, 
				null, 
				false,
				codes,
				arguments,
				null);
		result.addError(error);
	}
	
	public void addError(BindingResult result,String errorCode){
		result.reject(errorCode);
	}
}
