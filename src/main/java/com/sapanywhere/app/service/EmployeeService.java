package com.sapanywhere.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.Employee;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired EmployeeRepository employeeRepository;

	public void create(User user){
		Employee employ = new Employee();
		employ.setUser(user);
		employ.setFirstName(user.getFirstName());
		employ.setLastName(user.getLastName());
		employ.setEmail(user.getEmail());
		this.employeeRepository.save(employ);
	}
	
	public void save(Employee employee){
		Employee oldEmployee = employeeRepository.findOne(employee.getId());
		this.employeeRepository.save(employee);
	}
}
