package com.sapanywhere.app.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import com.sapanywhere.app.entity.OauthData;
import com.sapanywhere.app.entity.User;


public class BindForm extends BaseForm {
	public static final String FORM_NAME = "bindForm";

	public BindForm() {

	}

	public BindForm(OauthData oauthData) {
		this.sapAccount = oauthData.getUserEmail();
		this.email = oauthData.getUserEmail();
	}

	@NotNull
	@Size(min = 4, max = 30)
	private String sapAccount;

	@NotNull
	@Size(min = 4, max = 30)
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	@Email
	private String email;

	@NotNull
	@Size(min = 6, max = 30)
	private String password;

	@NotNull
	@Size(min = 6, max = 30)
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

	public String getSapAccount() {
		return sapAccount;
	}

	public void setSapAccount(String sapAccount) {
		this.sapAccount = sapAccount;
	}

	public User parse() {
		User user = new User();
		user.setSapAccount(this.sapAccount);
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setEmail(this.email);
		user.setPassword(this.password);
		return user;
	}

	@Override
	public void onValid(BindingResult result) {
		if (result.hasErrors()) {
			return;
		}

		if (!StringUtils.equals(this.password, this.repeatPassword)) {
			this.AddFieldError(result, FORM_NAME, "repeatPassword",
					new String[] { "bind.error.passwordnotmatch" }, null);
		}

	}

	public void onValid(BindingResult result, OauthData oauthData) {
		if (!StringUtils.equalsIgnoreCase(oauthData.getUserEmail(),
				this.getSapAccount())) {

			this.AddFieldError(result, FORM_NAME, "sapAccount",
					new String[] { "bind.error.sapaccountnotmatch" }, null);
			return;
		}

		this.onValid(result);
	}
	
	public void addFieldError(BindingResult result,String fieldName,String errorCode){
		this.AddFieldError(
				result,
				FORM_NAME,
				fieldName,
				new String[] { errorCode },
				null);
	}
}
