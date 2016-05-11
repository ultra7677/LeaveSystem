package com.sapanywhere.app.model.setting;

import com.sapanywhere.app.model.LeaveTypeRuleForm;

public class SettingPage {

	public SettingPage() {

	}

	public SettingPage(WorkHoursForm workHoursForm,
			ApproverListView approverListView,
			LeaveTypeRuleListView leaveTypeRuleListView,
			LeaveTypeRuleForm leaveTypeRuleForm) {
		this.workHoursForm = workHoursForm;
		this.approverListView = approverListView;
		this.leaveTypeRuleListView = leaveTypeRuleListView;
		this.leaveTypeRuleForm = leaveTypeRuleForm;
	}

	private WorkHoursForm workHoursForm;
	private ApproverListView approverListView;
	private LeaveTypeRuleListView leaveTypeRuleListView;
	private LeaveTypeRuleForm leaveTypeRuleForm;
	
	public ApproverListView getApproverListView() {
		return approverListView;
	}

	public void setApproverListView(ApproverListView approverListView) {
		this.approverListView = approverListView;
	}

	public LeaveTypeRuleListView getLeaveTypeRuleListView() {
		return leaveTypeRuleListView;
	}

	public void setLeaveTypeRuleListView(
			LeaveTypeRuleListView leaveTypeRuleListView) {
		this.leaveTypeRuleListView = leaveTypeRuleListView;
	}

	public WorkHoursForm getWorkHoursForm() {
		return workHoursForm;
	}

	public void setWorkHoursForm(WorkHoursForm workHoursForm) {
		this.workHoursForm = workHoursForm;
	}

	public LeaveTypeRuleForm getLeaveTypeRuleForm() {
		return leaveTypeRuleForm;
	}

	public void setLeaveTypeRuleForm(LeaveTypeRuleForm leaveTypeRuleForm) {
		this.leaveTypeRuleForm = leaveTypeRuleForm;
	}

}
