package com.sapanywhere.app.model.overview;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.sapanywhere.app.entity.Leave;
import com.sapanywhere.app.model.Pagination;

public class MyLeaveTable extends Pagination {

	public MyLeaveTable(Page<Leave> page) {
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
