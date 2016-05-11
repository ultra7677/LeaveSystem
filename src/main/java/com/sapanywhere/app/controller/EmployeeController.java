package com.sapanywhere.app.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sapanywhere.app.entity.Company;
import com.sapanywhere.app.entity.Employee;
import com.sapanywhere.app.entity.Files;
import com.sapanywhere.app.model.EmployeeInfoForm;
import com.sapanywhere.app.repository.EmployeeRepository;
import com.sapanywhere.app.service.CompanyService;
import com.sapanywhere.app.service.EmployeeService;
import com.sapanywhere.app.service.FileService;
import com.sapanywhere.app.service.user.UserInfo;

@Controller

public class EmployeeController {
	
	private static Logger logger = Logger.getLogger(CalendarController.class);
	private static String fileUrl = "/Users/ultra/Documents/images";
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private FileService fileService;

	@ModelAttribute("companyInfo")
	public Company getCompanyInfo(){
		return this.companyService.findById((long) 1);
	}
	
	@RequestMapping(value = "/information.html",method = RequestMethod.GET)
	public String createPage(@AuthenticationPrincipal UserInfo userInfo, EmployeeInfoForm employeeInfoForm){
		System.out.println(userInfo.getUser().getId());
	    Employee employee = employeeRepository.findByUser(userInfo.getUser());
	    System.out.println(employee.getFirstName());
	    employeeInfoForm.setByEmployee(employee);    
	    logger.info("load information.html");
		return "/employee/information";
	}
		
	@RequestMapping(value = "/edit.html",method = RequestMethod.GET)
	public String createEditForm(@AuthenticationPrincipal UserInfo userInfo,EmployeeInfoForm employeeInfoForm){
	    Employee employee = employeeRepository.findByUser(userInfo.getUser());
	    employeeInfoForm.setByEmployee(employee);    
		logger.info("load edit.html");
		return "/employee/edit";
	}
	
	@RequestMapping(value = "/editInfoForm", method = RequestMethod.POST)
	public String editEmployeeInfo(@AuthenticationPrincipal UserInfo userInfo,EmployeeInfoForm employeeInfoForm, BindingResult result) {
		if (result.hasErrors()) {
			return "/employee/information";
		}
		Employee employee = employeeRepository.findByUser(userInfo.getUser());
		this.employeeService.save(employeeInfoForm.parse(employeeInfoForm, employee));
		return "redirect:/information.html";
	}
	
	@RequestMapping(value = "/addImage", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	void addImage(@AuthenticationPrincipal UserInfo userInfo,@RequestParam("filename") String name,@RequestParam("data") String data) throws FileNotFoundException, IOException {
	    // your logic here
		System.out.println(name);
		System.out.println(data);
		
		String imageDataBytes = data.substring(data.indexOf(",")+1);
		byte[] image = Base64.decodeBase64(imageDataBytes);
		Files files = new Files();
		files.setFileName(name);
		files.setFileLocation(fileUrl+"/"+name);
		files.setType("image");
		fileService.create(files);
		
		
		Employee employee = employeeRepository.findByUser(userInfo.getUser());
		employee.setAvatarID(files.getId());
		this.employeeService.save(employee);
		try (OutputStream stream = new FileOutputStream(fileUrl+"/"+name)) {
		    stream.write(image);
		}
	}
	
	@RequestMapping(value = "/getImage/{avatarId}",method = RequestMethod.GET)
	public @ResponseBody void getImage(@PathVariable Long avatarId,HttpServletResponse response) throws IOException{
		System.out.println(avatarId);
		String fileUrl = fileService.findById(avatarId);
		if (fileUrl != null){
			FileInputStream file = new FileInputStream(fileUrl);
			  int i=file.available(); //得到文件大小   
		       byte data[]=new byte[i];   
		       file.read(data);  //读数据   
		       response.setContentType("image/*"); //设置返回的文件类型   
		       OutputStream outStream=response.getOutputStream(); //得到向客户端输出二进制数据的对象   
		       outStream.write(data);  //输出数据      
		       outStream.flush();  
		       outStream.close();   
		       file.close();   
		} 
	}
}
