package com.sapanywhere.app.model.setting;

import java.util.ArrayList;
import java.util.List;

import com.sapanywhere.app.dto.TimeDTO;
import com.sapanywhere.app.entity.WorkHours;

public class WorkHoursForm {

	public WorkHoursForm() {
		for (int index = 1; index <= 12; index++) {
			this.morningHours.add(new TimeDTO(index, String.format("time.%s.text", index)));
			this.afternoonHours.add(new TimeDTO(index + 12, String.format("time.%s.text", index + 12)));
		}
	}
	
	public WorkHoursForm(WorkHours workHours){
		this();
		if(workHours != null){
			this.morningStart = workHours.getMorningStart();
			this.moringEnd = workHours.getMoringEnd();
			this.afternoonStart = workHours.getAfternoonStart();
			this.afternoonEnd = workHours.getAfternoonEnd();
		}
	}

	private List<TimeDTO> morningHours = new ArrayList<TimeDTO>();
	private List<TimeDTO> afternoonHours = new ArrayList<TimeDTO>();
	private int morningStart;
	private int moringEnd;
	private int afternoonStart;
	private int afternoonEnd;

	public List<TimeDTO> getMorningHours() {
		return morningHours;
	}

	public List<TimeDTO> getAfternoonHours() {
		return afternoonHours;
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
