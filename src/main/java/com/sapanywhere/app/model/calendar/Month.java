package com.sapanywhere.app.model.calendar;

import java.util.ArrayList;
import java.util.List;

public class Month {

	public Month(int value){
		this.value = value;
	}
	
	private int value;
	
	private List<Week> weeks = new ArrayList<Week>();
	
	

	public int getValue() {
		return value;
	}

	public List<Week> getWeeks() {
		return weeks;
	}
}
