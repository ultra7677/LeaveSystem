package com.sapanywhere.app.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sapanywhere.app.dto.ApproverDTO;
import com.sapanywhere.app.entity.LeaveType;
import com.sapanywhere.app.entity.LeaveTypeRule;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.model.LeaveTypeRuleForm;
import com.sapanywhere.app.model.setting.ApproverListView;
import com.sapanywhere.app.model.setting.LeaveTypeRuleListView;
import com.sapanywhere.app.model.setting.SettingPage;
import com.sapanywhere.app.model.setting.WorkHoursForm;
import com.sapanywhere.app.repository.LeaveTypeRepository;
import com.sapanywhere.app.repository.LeaveTypeRuleRepository;
import com.sapanywhere.app.service.ApproverService;
import com.sapanywhere.app.service.LeaveDaysInfoService;
import com.sapanywhere.app.service.UserService;
import com.sapanywhere.app.service.WorkHoursService;

@Controller
public class SettingController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ApproverService approverService;
	
	@Autowired
	private WorkHoursService workHoursService;
	
	@Autowired 
	private LeaveDaysInfoService leaveDaysInfoService;
	
	@Autowired
	private LeaveTypeRuleRepository leaveTypeRuleRepository;
	
	@Autowired 
	private LeaveTypeRepository leaveTypeRepository;


	@RequestMapping(value = "/setting.html", method = RequestMethod.GET)
	public String loadLeaveIndexPage(SettingPage settingPage, Model model) {
		WorkHoursForm workHoursForm = new WorkHoursForm(workHoursService.first());
		//LeaveTypeRuleForm leaveTypeRuleForm = new LeaveTypeRuleForm();
		ApproverListView approverListView = new ApproverListView(this.approverService.findAllApproves(),this.userService.findAll());

		Iterator<LeaveType> leaveTypeIterator = this.leaveTypeRepository.findAll().iterator();
		List<LeaveType> leaveTypes = new ArrayList<LeaveType>();
		
		while (leaveTypeIterator.hasNext()){
			leaveTypes.add(leaveTypeIterator.next());
		}
		LeaveTypeRuleForm leaveTypeRuleForm = new LeaveTypeRuleForm();
		leaveTypeRuleForm.setLeaveTypes(leaveTypes);
		
		//for(int i=0;i<leaveTypes.size();i++)
			//System.out.println(leaveTypes.get(i).getName());

		settingPage.setWorkHoursForm(workHoursForm);
		settingPage.setApproverListView(approverListView);
		settingPage.setLeaveTypeRuleListView(new LeaveTypeRuleListView(this.leaveTypeRuleRepository.findAll()));
		settingPage.setLeaveTypeRuleForm(leaveTypeRuleForm);
		
		return "/settings/index";
	}

	@RequestMapping(value = "/setting/update", method = RequestMethod.POST)
	public String update(@Valid ApproverListView settingForm,BindingResult result) {
		if (result.hasErrors()) {
			return "/settings/index";
		}

		return "/settings/index";
	}

	@RequestMapping(value = "/setting/updateApprover", method = RequestMethod.POST)
	public @ResponseBody void setApprover(@RequestBody ApproverDTO approverDTO) throws Exception {
		if(approverDTO != null && approverDTO.getId()!= null && approverDTO.getApproverId() != null){
			this.approverService.update(approverDTO.getId(), approverDTO.getApproverId());
		}else{
			throw new Exception();
		}
	}

	@RequestMapping(value = "/setting/workHours", method = RequestMethod.POST)
	public String updateWorkHours(@Valid WorkHoursForm workHoursForm,
			BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return "/settings/index";
		}
		
		return "/settings/index";
		/*if(userApproverDTO != null && userApproverDTO.getId()!= null && userApproverDTO.getApproverId() != null){
			this.userApproverService.update(userApproverDTO.getId(), userApproverDTO.getApproverId());
		}else{
			throw new Exception();
		}*/
	}
	
	@RequestMapping(value = "/addLeaveTypeRule", method = RequestMethod.POST)
	public String addLeaveTypeRule(SettingPage settingPage, BindingResult result){
		LeaveTypeRuleForm leaveTypeRuleForm = settingPage.getLeaveTypeRuleForm();
		
		System.out.println(leaveTypeRuleForm.getLeaveTypeId());
		System.out.println(leaveTypeRuleForm.getBaseDays());
		
		LeaveType leaveType = this.leaveTypeRepository.findOne(leaveTypeRuleForm.getLeaveTypeId());
		// need error check 
		if(leaveType != null){
			LeaveTypeRule leaveTypeRule = new LeaveTypeRule();
			leaveTypeRule.setType(leaveType);
			leaveTypeRule.setBaseDays(leaveTypeRuleForm.getBaseDays());
			leaveTypeRule.setIncreaseDaysPerYear(leaveTypeRuleForm.getIncreaseDaysPerYear());
			leaveTypeRule.setMaxDays(leaveTypeRuleForm.getMaxDays());
			
			// get all users
			for(User user : this.userService.findAll()){
				this.leaveDaysInfoService.add(user,leaveTypeRule);
			}
			
			this.leaveTypeRuleRepository.save(leaveTypeRule);
		}
		
		return "redirect:/setting.html";
	}
	
	@RequestMapping(value = "/editLeaveTypeRule", method = RequestMethod.POST)
	public String editLeaveTypeRule(SettingPage settingPage){
		LeaveTypeRuleForm leaveTypeRuleForm = settingPage.getLeaveTypeRuleForm();
		System.out.println(leaveTypeRuleForm.getLeaveTypeId());
		System.out.println(leaveTypeRuleForm.getId());
		LeaveType leaveType = this.leaveTypeRepository.findOne(leaveTypeRuleForm.getLeaveTypeId());
		if(leaveType != null){
			LeaveTypeRule leaveTypeRule =  this.leaveTypeRuleRepository.findOne(leaveTypeRuleForm.getId());
			leaveTypeRule.setBaseDays(leaveTypeRuleForm.getBaseDays());
			leaveTypeRule.setIncreaseDaysPerYear(leaveTypeRuleForm.getIncreaseDaysPerYear());
			leaveTypeRule.setMaxDays(leaveTypeRuleForm.getMaxDays());
			leaveTypeRule.setType(leaveType);
			this.leaveTypeRuleRepository.save(leaveTypeRule);	
		}
		return "redirect:/setting.html";
	}
}
