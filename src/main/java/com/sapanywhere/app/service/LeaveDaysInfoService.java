package com.sapanywhere.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.LeaveTypeRule;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.entity.LeaveDaysInfo;
import com.sapanywhere.app.entity.WorkHours;
import com.sapanywhere.app.model.overview.LeaveInfo;
import com.sapanywhere.app.repository.LeaveDaysInfoRepository;

@Service
public class LeaveDaysInfoService {

	@Autowired
	private LeaveDaysInfoRepository leaveDaysInfoRepository;

	@Autowired
	private LeaveTypeRuleService leaveTypeRuleService;
	
	@Autowired
	private LeaveService leaveService;

	public void create(User user) {
		for (LeaveTypeRule rule : this.leaveTypeRuleService.findAll()) {
			LeaveDaysInfo days = new LeaveDaysInfo();
			days.setYear(LocalDate.now().getYear());
			days.setType(rule.getType());
			days.setUser(user);
			days.setDays(rule.getBaseDays());
			this.leaveDaysInfoRepository.save(days);
		}
	}
	
	public void add(User user,LeaveTypeRule rule){
		LeaveDaysInfo days = new LeaveDaysInfo();
		days.setYear(LocalDate.now().getYear());
		days.setType(rule.getType());
		days.setUser(user);
		days.setDays(rule.getBaseDays());
		this.leaveDaysInfoRepository.save(days);
	}
	
	public void save(User user,LeaveTypeRule rule){
		//LeaveDaysInfo days = this.leaveDaysInfoRepository.findOne(rule.getId());
		for(LeaveDaysInfo days : this.leaveDaysInfoRepository.findAllByUserAndType(user, rule.getType())){
			days.setYear(LocalDate.now().getYear());
			days.setType(rule.getType());
			days.setUser(user);
			days.setDays(rule.getBaseDays());
			this.leaveDaysInfoRepository.save(days);
		}
	}
	
	public List<LeaveInfo> findMyLeaveInfoThisYear(User user, WorkHours workHours){
		List<LeaveInfo> leaveInfos = new ArrayList<LeaveInfo>();
		List<LeaveDaysInfo> days = this.leaveDaysInfoRepository.findAllByUserAndYear(user,LocalDate.now().getYear());
		for(LeaveDaysInfo day : days){
			int totalLeaveHours = this.leaveService.getTotalLeaveHours(user, day.getType());
			LeaveInfo leaveInfo = new LeaveInfo(day,workHours);
			leaveInfo.setUsedHours(totalLeaveHours);
			leaveInfos.add(leaveInfo);
		}
		return leaveInfos;
	}
}
