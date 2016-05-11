package com.sapanywhere.app.service.user;

import org.springframework.security.core.authority.AuthorityUtils;
import com.sapanywhere.app.entity.User;

public class UserInfo extends org.springframework.security.core.userdetails.User{
	private static final long serialVersionUID = -8050466418909724802L;
	
	private User user;
	
	public UserInfo(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

	public Long getId(){
		return this.user.getId();
	}
	
	public String getEmail(){
		return this.user.getEmail();
	}
	
	public String getFullName(){
		return this.user.getFullName();
	}
	
	public User getUser(){
		return this.user;
	}
}
