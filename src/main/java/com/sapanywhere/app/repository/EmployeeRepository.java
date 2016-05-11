package com.sapanywhere.app.repository;

import org.springframework.data.repository.CrudRepository;
import com.sapanywhere.app.entity.Employee;
import com.sapanywhere.app.entity.User;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{
	
    Employee findByUser(User user);
}
