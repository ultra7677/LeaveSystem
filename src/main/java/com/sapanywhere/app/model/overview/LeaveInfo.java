package com.sapanywhere.app.model.overview;

import java.time.LocalDate;

import com.sapanywhere.app.entity.LeaveType;
import com.sapanywhere.app.entity.LeaveDaysInfo;
import com.sapanywhere.app.entity.WorkHours;

public class LeaveInfo {

	public LeaveInfo(LeaveDaysInfo days,WorkHours workHours) {
		this.leaveType = days.getType();
		this.fromDate = LocalDate.of(days.getYear(), 1, 1);
		this.toDate = this.fromDate.withDayOfYear(this.fromDate.lengthOfYear());
		this.setTotalHours(days.getDays() * workHours.getWorkHours());
	}

	private LeaveType leaveType;
	private LocalDate fromDate;
	private LocalDate toDate;
	private int totalHours;
	private int usedHours;

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public int getAvailableHours() {
		return this.totalHours - this.usedHours;
	}

	public int getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(int totalHours) {
		this.totalHours = totalHours;
	}

	public int getUsedHours() {
		return usedHours;
	}

	public void setUsedHours(int usedHours) {
		this.usedHours = usedHours;
	}
}
