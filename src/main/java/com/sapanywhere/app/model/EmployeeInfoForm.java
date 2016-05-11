package com.sapanywhere.app.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.sapanywhere.app.entity.Employee;

public class EmployeeInfoForm {
	@NotNull
	private String email;
	@NotNull
	private String name;
	private String birthDate;
	private String phoneNumber;
	private String department;
	private String position;
	private String entryDate;
	private String gender;
	private long avatarId;
	
	public void setByEmployee(Employee employee){
		this.setName(employee.getFirstName() + " " + employee.getLastName());
		this.setEmail(employee.getEmail());
		this.setPhoneNumber(employee.getPhoneNumber());
		this.setBirthDate(employee.getBirthDate());
		this.setDepartment(employee.getDepartment());
		this.setEntryDate(employee.getEntryDate());
		this.setGender(employee.getGender());
		this.setPosition(employee.getPosition());
		this.setAvatarId(employee.getAvatarID());
	}
	
	public Employee parse(EmployeeInfoForm employeeInfoForm, Employee employee){
		
		
		String[] ss = employeeInfoForm.getName().split(" ");
		
		employee.setFirstName(ss[0]);
		employee.setLastName(ss[1]);
		employee.setBirthDate(employeeInfoForm.getBirthDate());
		employee.setDepartment(employeeInfoForm.getDepartment());
		employee.setEmail(employeeInfoForm.getEmail());
		employee.setEntryDate(employeeInfoForm.getEntryDate());
		employee.setGender(employeeInfoForm.getGender());
		employee.setPosition(employeeInfoForm.getPosition());
		employee.setPhoneNumber(employeeInfoForm.getPhoneNumber());
		return employee;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(long avatarId) {
		this.avatarId = avatarId;
	}

}
