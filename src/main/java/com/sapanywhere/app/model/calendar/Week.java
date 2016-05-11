package com.sapanywhere.app.model.calendar;

import java.util.ArrayList;
import java.util.List;

public class Week {

	public Week() {

	}

	private List<Day> days = new ArrayList<Day>();

	public List<Day> getDays() {
		return days;
	}
}
