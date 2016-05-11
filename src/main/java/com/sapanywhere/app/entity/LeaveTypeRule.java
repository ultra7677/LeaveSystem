package com.sapanywhere.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class LeaveTypeRule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne
	@JoinColumn(name = "leaveType_id")
	private LeaveType type;

	private int baseDays;

	private int increaseDaysPerYear;
	
	private int maxDays;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LeaveType getType() {
		return type;
	}

	public void setType(LeaveType type) {
		this.type = type;
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
}
