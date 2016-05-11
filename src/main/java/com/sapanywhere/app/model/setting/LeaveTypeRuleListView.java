package com.sapanywhere.app.model.setting;

import java.util.ArrayList;

import com.sapanywhere.app.entity.LeaveTypeRule;

public class LeaveTypeRuleListView {

	public LeaveTypeRuleListView() {

	}

	public LeaveTypeRuleListView(Iterable<LeaveTypeRule> rules) {
		this.rules = rules;
	}

	private Iterable<LeaveTypeRule> rules = new ArrayList<LeaveTypeRule>();

	public Iterable<LeaveTypeRule> getRules() {
		return rules;
	}
}
