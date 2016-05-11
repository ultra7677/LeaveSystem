package com.sapanywhere.app.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.sapanywhere.app.entity.Leave;

public class ApproveLeaveTable extends Pagination {

	public ApproveLeaveTable(Page<Leave> page) {
		super(page);
		this.leaves.addAll(page.getContent());
	}

	private List<Leave> leaves = new ArrayList<Leave>();

	public List<Leave> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<Leave> leaves) {
		this.leaves = leaves;
	}
}
