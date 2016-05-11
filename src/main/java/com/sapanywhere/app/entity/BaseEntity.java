package com.sapanywhere.app.entity;

import java.time.LocalDate;

import javax.persistence.Column;

public class BaseEntity {

	@Column
	private LocalDate createTime;
	
	@Column
	private LocalDate updateTime;

	public LocalDate getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDate createTime) {
		this.createTime = createTime;
	}

	public LocalDate getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDate updateTime) {
		this.updateTime = updateTime;
	}
}
