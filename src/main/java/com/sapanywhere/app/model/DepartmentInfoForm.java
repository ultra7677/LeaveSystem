package com.sapanywhere.app.model;

import java.util.ArrayList;
import java.util.List;
import com.sapanywhere.app.entity.Department;


public class DepartmentInfoForm {

	private List<Department> departments = new ArrayList<Department>();
	
	public List<Department> getDepartments() {
		return departments;
	}
	
	// for add a new department 
	private String name;
	
	private int id;
	

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
