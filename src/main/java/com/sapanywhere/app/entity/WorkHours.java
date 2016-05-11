package com.sapanywhere.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class WorkHours {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private int morningStart;

	@Column
	private int moringEnd;

	@Column
	private int afternoonStart;

	@Column
	private int afternoonEnd;

	@Transient
	public int getWorkHours() {
		return (this.moringEnd - this.morningStart)
				+ (this.afternoonEnd - this.afternoonStart);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMorningStart() {
		return morningStart;
	}

	public void setMorningStart(int morningStart) {
		this.morningStart = morningStart;
	}

	public int getMoringEnd() {
		return moringEnd;
	}

	public void setMoringEnd(int moringEnd) {
		this.moringEnd = moringEnd;
	}

	public int getAfternoonStart() {
		return afternoonStart;
	}

	public void setAfternoonStart(int afternoonStart) {
		this.afternoonStart = afternoonStart;
	}

	public int getAfternoonEnd() {
		return afternoonEnd;
	}

	public void setAfternoonEnd(int afternoonEnd) {
		this.afternoonEnd = afternoonEnd;
	}
}
