package com.sapanywhere.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class LeaveType {

	public LeaveType(){
		
	}
	
	public LeaveType(long id){
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable= false,unique=true)
	private String name;
	
	@Column(nullable= false,unique=true)
	private String foreignName;

	public String getForeignName() {
		return foreignName;
	}

	public void setForeignName(String foreignName) {
		this.foreignName = foreignName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
