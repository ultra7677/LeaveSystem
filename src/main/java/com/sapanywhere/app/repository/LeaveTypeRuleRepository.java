package com.sapanywhere.app.repository;

import org.springframework.data.repository.CrudRepository;
import com.sapanywhere.app.entity.LeaveTypeRule;

public interface LeaveTypeRuleRepository extends
		CrudRepository<LeaveTypeRule, Long> {

}
