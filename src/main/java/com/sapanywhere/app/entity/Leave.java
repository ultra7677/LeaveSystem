package com.sapanywhere.app.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sapanywhere.app.enums.LeaveStatus;

@Entity
@Table
public class Leave extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(nullable= false)
	private LocalDate startDate;
	
	@Column(nullable= false)
	private LocalDate endDate;
	
	@Column
	private int startTime;
	
	@Column
	private int endTime;
	
	@Column
	private int total;
	
	@Column
	private int year;
	
	@Column(length= 1000)
	private String description;
	
	@OneToOne
	@JoinColumn(name = "leaveType_id")
	private LeaveType type;
	
	@Column
	private LeaveStatus status = LeaveStatus.NEW;
	
	@Transient
	public LocalDateTime getStartDateTime(){
		return LocalDateTime.of(this.startDate, LocalTime.of(this.startTime,0));
	}
	
	@Transient
	public LocalDateTime getEndDateTime(){
		return LocalDateTime.of(this.endDate, LocalTime.of(this.endTime,0));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public LeaveStatus getStatus() {
		return status;
	}

	public void setStatus(LeaveStatus status) {
		this.status = status;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
