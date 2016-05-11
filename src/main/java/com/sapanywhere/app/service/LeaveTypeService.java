package com.sapanywhere.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.LeaveType;
import com.sapanywhere.app.repository.LeaveTypeRepository;

@Service
public class LeaveTypeService {

	@Autowired
	private LeaveTypeRepository leaveTypeRepository;
	
	@Cacheable("leaveTypes")
	public Iterable<LeaveType> findAll(){
		return this.leaveTypeRepository.findAll();
	}
}
