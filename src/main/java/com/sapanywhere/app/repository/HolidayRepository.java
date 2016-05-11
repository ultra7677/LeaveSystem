package com.sapanywhere.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sapanywhere.app.entity.Holiday;

public interface HolidayRepository extends CrudRepository<Holiday, Long> {

	public List<Holiday> findAllByYear(int year);
	
	public Holiday findByYearAndMonthAndDay(int year, int month, int day);
}
