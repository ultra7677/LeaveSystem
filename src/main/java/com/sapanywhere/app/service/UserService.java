package com.sapanywhere.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.repository.UserRepository;
import com.sapanywhere.app.service.user.UserInfo;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ApproverService approverService;

	@Autowired
	private LeaveDaysInfoService leaveDaysInfoService;
	
	@Autowired
	private EmployeeService employeeService;

	public User create(User user) {
		this.userRepository.save(user);
		this.approverService.create(user);
		this.leaveDaysInfoService.create(user);
		this.employeeService.create(user);
		return user;
	}
	
	public User findBySAPAccount(String sapAccount) {
		return this.userRepository.findBySapAccount(sapAccount);
	}
	
	public boolean autoLogin(String email) {
		try {
			User user = this.findByEmail(email);
			if (user != null) {
				UserInfo userInfo = new UserInfo(user);
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
						userInfo, null, userInfo.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(token);
				
				return true;
			}
		} catch (Exception e) {
			SecurityContextHolder.getContext().setAuthentication(null);
		
		}

		return false;
	}
	
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
	public boolean exist(String email){
		return this.userRepository.findByEmail(email) != null;
	}

	public Iterable<User> findAll() {
		return this.userRepository.findAll();
	}
}
