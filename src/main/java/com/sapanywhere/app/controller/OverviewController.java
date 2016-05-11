package com.sapanywhere.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.sapanywhere.app.model.ApproveLeaveTable;
import com.sapanywhere.app.model.overview.MyLeaveTable;
import com.sapanywhere.app.model.overview.OverviewPage;
import com.sapanywhere.app.service.ApproverService;
import com.sapanywhere.app.service.LeaveDaysInfoService;
import com.sapanywhere.app.service.LeaveService;
import com.sapanywhere.app.service.WorkHoursService;
import com.sapanywhere.app.service.user.UserInfo;

@Controller
public class OverviewController {

	@Autowired
	private ApproverService approverService;
	
	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private LeaveDaysInfoService leaveDaysInfoService;
	
	@Autowired
	private WorkHoursService workHoursService;
	
	@RequestMapping(value = "/overview.html", method = RequestMethod.GET)
	public String loadLeaveRequestPage(@RequestParam(value="myLeavePage",required = false) String myLeavePage,
			@RequestParam(value="approveLeavePage",required = false) String approveLeavePage,
			Model model, 
			@AuthenticationPrincipal UserInfo userInfo) {		
		MyLeaveTable myLeaveTable = new MyLeaveTable(this.leaveService.findLeavesByUser(userInfo.getUser(), parsePageIndex(myLeavePage)));
		ApproveLeaveTable approveLeaveTable = new ApproveLeaveTable(this.approverService.findLeaves(userInfo.getUser(), parsePageIndex(approveLeavePage)));
				
		OverviewPage overview = new OverviewPage();
		
		if (approveLeaveTable.getLeaves().size() == 0)  overview.setShowWaitApproveList(false);
		else  overview.setShowWaitApproveList(true);
		
		if (myLeaveTable.getLeaves().size() == 0) overview.setShowMyLeaves(false);
		else overview.setShowMyLeaves(true);
		
		//System.out.println("size is"+approveLeaveTable.getLeaves().size());
		//System.out.println("size is"+overview.isShowWaitApproveList());
		
		overview.setWorkHours(this.workHoursService.first());
		overview.setMyLeaveTable(myLeaveTable);
		overview.setApproveLeaveTable(approveLeaveTable);
		overview.setLeaveInfos(this.leaveDaysInfoService.findMyLeaveInfoThisYear(userInfo.getUser(),overview.getWorkHours()));
		model.addAttribute(overview);
		
		return "/overview/index";
	}
	
	private int parsePageIndex(String pageIndex){
		int index = 0;
		if(!StringUtils.isEmpty(pageIndex)){
			index = Integer.parseInt(pageIndex);
			if(index > 1){
				index = index - 1;
			}else{
				index = 0;
			}
		}
		
		return index;
	}
}
