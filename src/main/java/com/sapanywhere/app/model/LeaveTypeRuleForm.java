package com.sapanywhere.app.model;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.BindingResult;

import com.sapanywhere.app.entity.LeaveType;

public class LeaveTypeRuleForm extends BaseForm{
	private long leaveTypeId;
	private String leaveTypeName;
	public static final String FORMNAME = "leaveTypeRuleForm";
	
	@NotNull
	@Min(value = 0)
	@Max(value = 365)
	private int baseDays;
	
	@NotNull
	@Min(value = 0)
	@Max(value = 365)
	private int increaseDaysPerYear;
	
	@NotNull
	@Min(value = 0)
	@Max(value = 365)
	private int maxDays;
	
	private long id;
	
	private List<LeaveType> leaveTypes;
	

	public String getLeaveTypeName() {
		return leaveTypeName;
	}
	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}
	public int getBaseDays() {
		return baseDays;
	}
	public void setBaseDays(int baseDays) {
		this.baseDays = baseDays;
	}
	public int getIncreaseDaysPerYear() {
		return increaseDaysPerYear;
	}
	public void setIncreaseDaysPerYear(int increaseDaysPerYear) {
		this.increaseDaysPerYear = increaseDaysPerYear;
	}
	public int getMaxDays() {
		return maxDays;
	}
	public void setMaxDays(int maxDays) {
		this.maxDays = maxDays;
	}
	public List<LeaveType> getLeaveTypes() {
		return leaveTypes;
	}
	public void setLeaveTypes(List<LeaveType> leaveTypes) {
		this.leaveTypes = leaveTypes;
	}
	public long getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(long leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public void onValid(BindingResult result) {
		return;
	}

}
