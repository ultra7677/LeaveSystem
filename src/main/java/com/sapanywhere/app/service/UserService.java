package com.sapanywhere.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.repository.UserRepository;

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

	public void create(User user) {
		this.userRepository.save(user);
		this.approverService.create(user);
		this.leaveDaysInfoService.create(user);
		this.employeeService.create(user);
	}
	
	public boolean exist(String email){
		return this.userRepository.findByEmail(email) != null;
	}

	public Iterable<User> findAll() {
		return this.userRepository.findAll();
	}
}
