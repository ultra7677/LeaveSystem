package com.sapanywhere.app.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.validation.BindingResult;

import com.sapanywhere.app.entity.Leave;
import com.sapanywhere.app.entity.LeaveType;

public class LeaveRequestForm extends BaseForm {
	private static final String FORMNAME = "leaveRequestForm";

	@NotNull
	private Long leaveType;

	@DateTimeFormat(iso = ISO.DATE)
	@NotNull
	private LocalDate startDate = LocalDate.now();

	@DateTimeFormat(iso = ISO.DATE)
	@NotNull
	private LocalDate endDate = LocalDate.now();

	@NotNull
	private int startTime;

	@NotNull
	private int endTime;

	private String approverName;

	private String description;

	public Long getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(Long leaveType) {
		this.leaveType = leaveType;
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

	public Leave parse() {
		Leave leave = new Leave();
		leave.setStartDate(this.startDate);
		leave.setEndDate(this.endDate);
		leave.setStartTime(this.startTime);
		leave.setEndTime(this.endTime);
		leave.setDescription(this.description);
		leave.setType(new LeaveType(this.leaveType));
		return leave;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	@Override
	public void onValid(BindingResult result) {
		if(result.hasErrors()){
			return;
		}
		
		if (this.startDate.isAfter(this.endDate)) {
			this.AddFieldError(
					result,
					FORMNAME,
					"endDate",
					new String[] { "leave.request.error.endDateLessThanStartDate" },
					null);
		}
		
		if(this.startDate.isEqual(this.endDate) && this.startTime > this.endTime){
			this.AddFieldError(
					result,
					FORMNAME,
					"endTime",
					new String[] { "leave.request.error.endTimeLessThanStartTime" },
					null);
		}
		
		//leave request has exceeded the number of available days
		
	}
}
