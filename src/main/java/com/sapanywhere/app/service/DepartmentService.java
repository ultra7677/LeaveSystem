package com.sapanywhere.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.Department;
import com.sapanywhere.app.repository.DepartmentRepository;

@Service 
public class DepartmentService {
	@Autowired DepartmentRepository departmentRepository;
	
	public void create(Department department){
		this.departmentRepository.save(department);
	}
	
	public Department findById(Long id){
		return this.departmentRepository.findOne(id);
	}
	
	public Iterable<Department> findAll() {
		return this.departmentRepository.findAll();
	}
	
	public void save(Department department){
		this.departmentRepository.save(department);
	}
}
