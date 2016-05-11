package com.sapanywhere.app.service;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.WorkDay;
import com.sapanywhere.app.repository.WorkDayRepository;

@Service
public class WorkDayService {

	@Autowired
	private WorkDayRepository workDayRepository;
	
	@Cacheable("workday")
	public WorkDay first() {
		Iterator<WorkDay> workdayIterator = this.workDayRepository
				.findAll().iterator();
		if (workdayIterator.hasNext()) {
			return workdayIterator.next();
		}

		
		return null;
	}
}
