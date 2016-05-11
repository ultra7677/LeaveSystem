package com.sapanywhere.app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.WorkHours;

@Service
public class FormatterService {

	@Autowired
	private WorkHoursService workHoursService;
	
	@Autowired
	private LocaleService localeService; 
	
	public String formatDate(LocalDateTime dateTime){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
		return dateTime.format(formatter);
	}
	
	public String formatTotalHours(int totalHours){
		int hours = totalHours;
		WorkHours workHours = this.workHoursService.first();
		if(totalHours < workHours.getWorkHours()){
			if(totalHours == 1){
				return localeService.getMessage("overview.table.total.hour_format", new Object[] {hours});
			}
			
			return localeService.getMessage("overview.table.total.hour_plural_format", new Object[] {hours});
		}
		
		int days = totalHours / workHours.getWorkHours();
		hours = totalHours % workHours.getWorkHours();
		if(hours == 0){
			if(days == 1){
				return localeService.getMessage("overview.table.total.day_format", new Object[] {days});
			}
			
			return localeService.getMessage("overview.table.total.day_plural_format", new Object[] {days});
		}
		
		if(days == 1){
			if(hours == 1){
				return localeService.getMessage("overview.table.total.day_hour_format", new Object[] {days,hours});
			}else{
				return localeService.getMessage("overview.table.total.day_hour_plural_format", new Object[] {days,hours});
			}
		}
		return localeService.getMessage("overview.table.total.day__plural_hour_plural_format", new Object[] {days,hours});
	}
}
