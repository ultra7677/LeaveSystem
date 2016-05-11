package com.sapanywhere.app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.sapanywhere.app.entity.Leave;
import com.sapanywhere.app.entity.LeaveType;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.enums.LeaveStatus;

public class LeaveDTO {
	public LeaveDTO(Leave leave){
		this.id = leave.getId();
		this.user = leave.getUser();
		//this.startDate = new LocalDateTime(leave.getStartDate(),new LocalTime());
	}
	
	private long id;
	private User user;
	private LocalDateTime startDate;
	private LocalDate endDate;
	private int total;
	private int year;
	private String description;
	private LeaveType type;
	private LeaveStatus status = LeaveStatus.NEW;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LeaveType getType() {
		return type;
	}
	public void setType(LeaveType type) {
		this.type = type;
	}
	public LeaveStatus getStatus() {
		return status;
	}
	public void setStatus(LeaveStatus status) {
		this.status = status;
	}
}
