package com.sapanywhere.app.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.sapanywhere.app.entity.Department;
import com.sapanywhere.app.entity.Files;
import com.sapanywhere.app.model.CompanyInfoForm;
import com.sapanywhere.app.model.DepartmentInfoForm;
import com.sapanywhere.app.model.setting.SettingPage;
import com.sapanywhere.app.service.CompanyService;
import com.sapanywhere.app.service.DepartmentService;
import com.sapanywhere.app.service.FileService;
import com.sapanywhere.app.service.user.UserInfo;

@Controller

public class CompanyController {
	private static Logger logger = Logger.getLogger(CalendarController.class);
	private static String fileUrl = "/Users/ultra/Documents/images";
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@ModelAttribute("myDepartment")
	public Department getDepartment(){
		return this.departmentService.findById((long)1);
	}

	@RequestMapping(value = "/companyinfo.html",method = RequestMethod.GET)
	public String showCompanyInfo(CompanyInfoForm companyInfoForm, DepartmentInfoForm departmentInfoForm){
		Company company = this.companyService.findById((long) 1);
		companyInfoForm.setName(company.getName());
		companyInfoForm.setAvatarId(company.getLogoId());
	
		Iterator<Department> departmentIterator = this.departmentService.findAll().iterator();
		List<Department> departments = new ArrayList<Department>();
		
		while (departmentIterator.hasNext())		
			departments.add( departmentIterator.next());

		//System.out.println(departments);
		if(null != departments && departments.size() != 0 ){
			departmentInfoForm.setDepartments(departments);	
			//for(int i=0;i<departmentInfoForm.getDepartments().size();i++)
				System.out.println(departmentInfoForm.getDepartments());
		}
		logger.info("load company info");
		return "company/companyinfo";
	}
	
	@RequestMapping(value = "/addLogo", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
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
		System.out.println(files.getId());
		Company company = this.companyService.findById((long) 1);
		company.setLogoId(files.getId());
		this.companyService.save(company);
		try (OutputStream stream = new FileOutputStream(fileUrl+"/"+name)) {
		    stream.write(image);
		}
	}
	
	@RequestMapping(value = "/getLogo/{avatarId}",method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/editCompanyInfoForm", method = RequestMethod.POST)
	public String editCompanyInfo(@AuthenticationPrincipal UserInfo userInfo,SettingPage settingPage, BindingResult result) {
		if (result.hasErrors()) {
			return "/company/companyinfo";
		}
		CompanyInfoForm companyInfoForm = settingPage.getCompanyInfoForm();
		Company company = this.companyService.findById((long) 1);
	//	company.setLogoId(companyInfoForm.getAvatarId());
		company.setName(companyInfoForm.getName());
		this.companyService.save(company);
		return "redirect:/setting.html";
	}
	
	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
	public String addDepartment(SettingPage settingPage){
		DepartmentInfoForm departmentInfoForm = settingPage.getDepartmentInfoForm();
		System.out.println(departmentInfoForm.getName());
		Department department = new Department();
		department.setName(departmentInfoForm.getName());
		this.departmentService.create(department);
		return "redirect:/setting.html";
	}
	
	@RequestMapping(value = "/editDepartment", method = RequestMethod.POST)
	public String editDepartment(SettingPage settingPage){
		DepartmentInfoForm departmentInfoForm = settingPage.getDepartmentInfoForm();
		Department department = this.departmentService.findById((long) departmentInfoForm.getId());
		department.setName(departmentInfoForm.getName());
		this.departmentService.save(department);
		return "redirect:/setting.html";
	}
	
}
