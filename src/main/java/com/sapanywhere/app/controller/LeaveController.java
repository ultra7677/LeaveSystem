package com.sapanywhere.app.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.entity.WorkHours;
import com.sapanywhere.app.exception.BusinessException;
import com.sapanywhere.app.model.LeaveRequestForm;
import com.sapanywhere.app.service.ApproverService;
import com.sapanywhere.app.service.LeaveService;
import com.sapanywhere.app.service.WorkHoursService;
import com.sapanywhere.app.service.user.UserInfo;

@Controller
public class LeaveController {

	private static Logger logger = Logger.getLogger(LeaveController.class);

	@Autowired
	private ApproverService approverService;

	@Autowired
	private LeaveService leaveService;

	@Autowired
	private WorkHoursService workHoursService;

	@RequestMapping(value = "/leave/request.html", method = RequestMethod.GET)
	public String loadLeaveRequestPage(LeaveRequestForm leaveRequestForm, @AuthenticationPrincipal UserInfo userInfo) {
		User approver = this.approverService.getApprover(userInfo.getUser());
		if(approver == null){
			logger.info("You hasn't an approver, please contact your administrator.");
			return "/leave/noapprover";
		}
		
		WorkHours workHours = this.workHoursService.first();
		leaveRequestForm.setStartTime(workHours.getMorningStart());
		leaveRequestForm.setEndTime(workHours.getAfternoonEnd());
		leaveRequestForm.setApproverName(approver.getFullName());
		return "/leave/request";
	}

	@RequestMapping(value = "/leave/request", method = RequestMethod.POST)
	public String LeaveRequest(@Valid LeaveRequestForm leaveRequestForm,
			BindingResult result, 
			@AuthenticationPrincipal UserInfo userInfo){
		leaveRequestForm.onValid(result);
		if (result.hasErrors()) {
			return "/leave/request";
		}
		
		try {
			this.leaveService.create(leaveRequestForm, userInfo.getUser());
		} catch (BusinessException ex) {
			result.reject(ex.getCode());
			return "/leave/request";
		}

		return "redirect:/overview.html";
	}

	@RequestMapping(value = "/leave({id:[0-9]+})/cancel", method = RequestMethod.POST)
	public @ResponseBody void cancel(@PathVariable Long id,
			@AuthenticationPrincipal UserInfo userInfo) throws Exception {
		this.leaveService.cancel(id, userInfo.getUser());
	}

	@RequestMapping(value = "/leave({id:[0-9]+})/approve", method = RequestMethod.POST)
	public @ResponseBody void approve(@PathVariable Long id,
			@AuthenticationPrincipal UserInfo userInfo) throws Exception {
		this.leaveService.approve(id, userInfo.getUser());
	}

	@RequestMapping(value = "/leave({id:[0-9]+})/reject", method = RequestMethod.POST)
	public @ResponseBody void reject(@PathVariable Long id,
			@AuthenticationPrincipal UserInfo userInfo) throws Exception {
		this.leaveService.reject(id, userInfo.getUser());
	}
}
