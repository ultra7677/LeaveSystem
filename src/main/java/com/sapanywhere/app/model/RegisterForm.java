package com.sapanywhere.app.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.codec.binary.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;

import com.sapanywhere.app.entity.User;

public class RegisterForm extends BaseForm{
	public static final String FORMNAME = "registerForm";
	
	@NotNull
	@Size(min=4, max=30)
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Size(min=6, max=30)
	private String password;
	
	@NotNull
	@Size(min=6, max=30)
	private String repeatPassword;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public User parse() {
		User user = new User();
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setEmail(this.email);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(this.password));
		return user;
	}

	@Override
	public void onValid(BindingResult result) {
	
		if(result.hasErrors()){
			return;
		}
	
		if(this.email.length() == 0){
			this.AddFieldError(result, FORMNAME, "email", new String[] {"register.error.emailisempty"}, null);
		}
		
		if(!StringUtils.equals(this.password, this.repeatPassword)){
			this.AddFieldError(
					result,
					FORMNAME,
					"repeatPassword",
					new String[] { "register.error.passwordnotmatch" },
					null);
		}
		
	}
}
