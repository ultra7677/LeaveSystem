package com.sapanywhere.app.model.setting;

import com.sapanywhere.app.model.CompanyInfoForm;
import com.sapanywhere.app.model.DepartmentInfoForm;
import com.sapanywhere.app.model.LeaveTypeRuleForm;

public class SettingPage {

	public SettingPage() {

	}

	public SettingPage(WorkHoursForm workHoursForm,
			ApproverListView approverListView,
			LeaveTypeRuleListView leaveTypeRuleListView,
			LeaveTypeRuleForm leaveTypeRuleForm,
			CompanyInfoForm companyInfoForm,
			DepartmentInfoForm departmentInfoForm) {
		this.workHoursForm = workHoursForm;
		this.approverListView = approverListView;
		this.leaveTypeRuleListView = leaveTypeRuleListView;
		this.leaveTypeRuleForm = leaveTypeRuleForm;
		this.companyInfoForm = companyInfoForm;
		this.departmentInfoForm = departmentInfoForm;
	}

	private WorkHoursForm workHoursForm;
	private ApproverListView approverListView;
	private LeaveTypeRuleListView leaveTypeRuleListView;
	private LeaveTypeRuleForm leaveTypeRuleForm;
	private CompanyInfoForm companyInfoForm;
	private DepartmentInfoForm departmentInfoForm;
	
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

	public DepartmentInfoForm getDepartmentInfoForm() {
		return departmentInfoForm;
	}

	public void setDepartmentInfoForm(DepartmentInfoForm departmentInfoForm) {
		this.departmentInfoForm = departmentInfoForm;
	}

	public CompanyInfoForm getCompanyInfoForm() {
		return companyInfoForm;
	}

	public void setCompanyInfoForm(CompanyInfoForm companyInfoForm) {
		this.companyInfoForm = companyInfoForm;
	}

}
