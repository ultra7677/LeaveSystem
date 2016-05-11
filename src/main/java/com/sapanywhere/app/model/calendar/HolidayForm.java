package com.sapanywhere.app.model.calendar;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.sapanywhere.app.entity.Holiday;
import com.sapanywhere.app.enums.HolidayType;

public class HolidayForm {

	@DateTimeFormat(iso = ISO.DATE)
	@NotNull
	private LocalDate date = LocalDate.now();
	
	@NotNull
	private String name;
	
	@NotNull
	private String foreignName;
	
	@NotNull
	private HolidayType holidayType;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getForeignName() {
		return foreignName;
	}

	public void setForeignName(String foreignName) {
		this.foreignName = foreignName;
	}

	public HolidayType getHolidayType() {
		return holidayType;
	}

	public void setHolidayType(HolidayType holidayType) {
		this.holidayType = holidayType;
	}
	
	
	public Holiday parse(){
		Holiday holiday = new Holiday();
		holiday.setYear(this.date.getYear());
		holiday.setMonth(this.date.getMonthValue());
		holiday.setDay(this.date.getDayOfMonth());
		holiday.setName(this.name);
		holiday.setForeignName(this.foreignName);
		holiday.setHolidayType(this.holidayType);
		return holiday;
	}
}
