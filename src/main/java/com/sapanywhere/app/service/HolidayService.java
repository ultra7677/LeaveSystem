package com.sapanywhere.app.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.Holiday;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.exception.BusinessException;
import com.sapanywhere.app.repository.HolidayRepository;

@Service
public class HolidayService {

	private static Logger logger = Logger.getLogger(HolidayService.class);
	
	@Autowired
	private HolidayRepository holidayRepository;

	public void create(Holiday holiday) throws BusinessException {
		if (this.exist(holiday.getDate())) {
			logger.error(String.format("The holiday %s is existl.", holiday.getDate()));
			throw new BusinessException("error.holiday.create.duplicate");
		}
		
		this.holidayRepository.save(holiday);
	}

	public List<Holiday> findAllHolidaysThisYear() {
		return this.holidayRepository.findAllByYear(LocalDate.now().getYear());
	}
	
	public void delete(long id,User user){
		this.holidayRepository.delete(id);
	}
	
	public boolean exist(LocalDate date){
		Holiday holiday = this.holidayRepository.findByYearAndMonthAndDay(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
		return holiday != null;
	}
}
