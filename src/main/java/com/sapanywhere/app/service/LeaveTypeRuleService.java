package com.sapanywhere.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.LeaveTypeRule;
import com.sapanywhere.app.repository.LeaveTypeRuleRepository;

@Service
public class LeaveTypeRuleService {

	@Autowired
	private LeaveTypeRuleRepository leaveTypeRuleRepository;
	
	public Iterable<LeaveTypeRule> findAll(){
		return this.leaveTypeRuleRepository.findAll();
	}
	
	public LeaveTypeRule findById(Long id){
		return this.leaveTypeRuleRepository.findOne(id);
	}
}
