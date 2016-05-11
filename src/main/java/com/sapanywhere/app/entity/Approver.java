package com.sapanywhere.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Approver {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne 
	@JoinColumn(name = "approver_id")
	private User approver;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}
}
