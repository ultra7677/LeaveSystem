package com.sapanywhere.app.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.WorkHours;
import com.sapanywhere.app.exception.BusinessException;
import com.sapanywhere.app.repository.WorkHoursRepository;

@Service
public class WorkHoursService {

	@Autowired
	private WorkHoursRepository workHoursRepository;
	
	@Autowired
	private HolidayService holidayService;

	@Cacheable("workhours")
	public WorkHours first() {
		Iterator<WorkHours> workHoursIterator = this.workHoursRepository
				.findAll().iterator();
		if (workHoursIterator.hasNext()) {
			return workHoursIterator.next();
		}

		return null;
	}

	public int calcOffset(LocalDate startDate,LocalDate endDate,int startTime,int endTime) throws BusinessException{
		if(endDate.isBefore(startDate)){
			throw new BusinessException("");
		}
		
		if(this.isSameDay(startDate, endDate)){
			if(startTime >= endTime){
				throw new BusinessException("");
			}
		}

		int totalDays = 0;
		LocalDate temp = startDate.plusDays(1);
		while(temp.isBefore(endDate)){
			if(!this.isHoliday(temp)){
				totalDays += 1;
			}
			temp = temp.plusDays(1);
		}

		WorkHours workHours = this.first();
		
		int deltaHours = 0;
		if(this.isSameDay(startDate, endDate)){
			if(!this.isHoliday(startDate)){
				deltaHours = this.getTotalHoursOfDay(workHours, startTime, endTime);
			}
		}else{
			if(this.isHoliday(startDate)){
				if(!this.isHoliday(endDate)){
					deltaHours = this.getTotalHoursOfDay(workHours, workHours.getMorningStart(), endTime);
				}
			}else{
				deltaHours = this.getTotalHoursOfDay(workHours, startTime, workHours.getAfternoonEnd());
				if(!this.isHoliday(endDate)){
					deltaHours += this.getTotalHoursOfDay(workHours, workHours.getMorningStart(), endTime);
				}
			}
		}

		
		return totalDays * workHours.getWorkHours() + deltaHours;
	}
	
	public List<Integer> getWorkHours(){
		List<Integer> hours = new ArrayList<Integer>();
		WorkHours workHours = this.first();
		for (int index = 1; index <= 24; index++) {
			boolean inMorning = index>= workHours.getMorningStart() && index <= workHours.getMoringEnd();
			boolean inAfternoon = index>= workHours.getAfternoonStart() && index <= workHours.getAfternoonEnd();
			if(inMorning ||inAfternoon ){
				hours.add(index);
			}
		}
		
		return hours;
	}
	
	
	private int getTotalHoursOfDay(WorkHours workHours,int startHours, int endHours){
		int deltaHours = 0;
		int tempTime = startHours;
		while(tempTime <= endHours){
			if((tempTime> workHours.getMorningStart() && tempTime <= workHours.getMoringEnd()) || 
			   (tempTime> workHours.getAfternoonStart() && tempTime <= workHours.getAfternoonEnd())){
				deltaHours += 1;
			}
			
			tempTime += 1;
		}
		
		return deltaHours;
	}
	
	private boolean isHoliday(LocalDate date){
		if(date.getDayOfWeek() == DayOfWeek.SATURDAY ||  date.getDayOfWeek() == DayOfWeek.SUNDAY){
			return true;
		}
		
		//Festival Day
		if(this.holidayService.exist(date)){
			return true;
		}
		
		return false;
	}
	
	private boolean isSameDay(LocalDate date1,LocalDate date2){
		return date1.getYear() == date2.getYear() && 
				date1.getMonth() == date2.getMonth() && 
				date1.getDayOfMonth() == date2.getDayOfMonth();
	}
	
	public int getWorkHoursPerDay(){
		WorkHours workHours = this.first();
		return workHours.getWorkHours();
	}
}
