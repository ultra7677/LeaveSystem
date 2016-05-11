package com.sapanywhere.app.model.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sapanywhere.app.entity.Holiday;

public class CalendarPage {

	private List<Holiday> holidays;

	public CalendarPage(List<Holiday> holidays) {
		this.holidays = holidays;
		int year = LocalDate.now().getYear();
		for (int monthIndex = 1; monthIndex <= 12; monthIndex++) {
			LocalDate date = LocalDate.of(year, monthIndex, 1);
			Month month = new Month(monthIndex);
			Week week = new Week();
			month.getWeeks().add(week);
			for (int dayIndex = 1; dayIndex <= date.lengthOfMonth(); dayIndex++) {
				Day day = new Day(year, monthIndex, dayIndex);
				day.setHoliday(this.findHoliday(year, monthIndex, dayIndex));
				if (week.getDays().size() > 0
						&& day.getDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
					week = new Week();
					month.getWeeks().add(week);
				}

				week.getDays().add(day);
			}
			this.months.add(month);
		}
	}

	private List<Month> months = new ArrayList<Month>();

	public List<Month> getMonths() {
		return months;
	}

	public List<Holiday> getHolidays() {
		return holidays;
	}

	private Holiday findHoliday(int year, int month, int day) {
		for (Holiday holiday : holidays) {
			if (holiday.getYear() == year && holiday.getMonth() == month
					&& holiday.getDay() == day) {
				return holiday;
			}
		}

		return null;
	}

}
