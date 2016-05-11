package com.sapanywhere.app.model.calendar;

import java.time.LocalDate;

import com.sapanywhere.app.entity.Holiday;

public class Day {

	public Day(int year,int month, int day){
		this.date = LocalDate.of(year, month, day);
		this.today = this.date.isEqual(LocalDate.now());
	}
	
	private LocalDate date;
	private boolean today;
	private Holiday holiday;
	
	public LocalDate getDate() {
		return date;
	}
	
	public boolean isToday() {
		return today;
	}

	public Holiday getHoliday() {
		return holiday;
	}

	public void setHoliday(Holiday holiday) {
		this.holiday = holiday;
	}

}
