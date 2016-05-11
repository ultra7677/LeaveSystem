package com.sapanywhere.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.repository.UserRepository;
import com.sapanywhere.app.service.user.UserInfo;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(email);
		if(user == null){
			throw new UsernameNotFoundException(String.format("User with email=%s was not found", email));
		}
		return new UserInfo(user);
	}

}
