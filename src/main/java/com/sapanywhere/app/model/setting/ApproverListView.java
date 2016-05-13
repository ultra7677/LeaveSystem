package com.sapanywhere.app.model.setting;

import java.util.ArrayList;

import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.entity.Approver;

public class ApproverListView {

	public ApproverListView() {

	}

	public ApproverListView(Iterable<Approver> approvers, Iterable<User> users) {
		this.setApprovers(approvers);
		this.users = users;
	}
	

	private Iterable<Approver> approvers = new ArrayList<Approver>();
	private Iterable<User> users = new ArrayList<User>();

	public Iterable<User> getUsers() {
		return users;
	}

	public void setUsers(Iterable<User> users) {
		this.users = users;
	}

	public Iterable<Approver> getApprovers() {
		return approvers;
	}

	public void setApprovers(Iterable<Approver> approvers) {
		this.approvers = approvers;
	}

}
