package com.sapanywhere.app.model.overview;

import java.util.ArrayList;
import java.util.List;

import com.sapanywhere.app.entity.WorkHours;
import com.sapanywhere.app.model.ApproveLeaveTable;

public class OverviewPage {
	private ApproveLeaveTable approveLeaveTable;
	private MyLeaveTable myLeaveTable;
	private List<LeaveInfo> leaveInfos = new ArrayList<LeaveInfo>();
	private WorkHours workHours;
	
	private boolean showWaitApproveList;
	private boolean showMyLeaves;
	
	public List<LeaveInfo> getLeaveInfos() {
		return leaveInfos;
	}

	public void setLeaveInfos(List<LeaveInfo> leaveInfos) {
		this.leaveInfos = leaveInfos;
	}



	public ApproveLeaveTable getApproveLeaveTable() {
		return approveLeaveTable;
	}

	public void setApproveLeaveTable(ApproveLeaveTable approveLeaveTable) {
		this.approveLeaveTable = approveLeaveTable;
	}

	public MyLeaveTable getMyLeaveTable() {
		return myLeaveTable;
	}

	public void setMyLeaveTable(MyLeaveTable myLeaveTable) {
		this.myLeaveTable = myLeaveTable;
	}

	public WorkHours getWorkHours() {
		return workHours;
	}

	public void setWorkHours(WorkHours workHours) {
		this.workHours = workHours;
	}

	public boolean isShowWaitApproveList() {
		return showWaitApproveList;
	}

	public void setShowWaitApproveList(boolean showWaitApproveList) {
		this.showWaitApproveList = showWaitApproveList;
	}

	public boolean isShowMyLeaves() {
		return showMyLeaves;
	}

	public void setShowMyLeaves(boolean showMyLeaves) {
		this.showMyLeaves = showMyLeaves;
	}
}
