package com.sapanywhere.app.repository;
import org.springframework.data.repository.CrudRepository;
import com.sapanywhere.app.entity.Department;

public interface DepartmentRepository extends CrudRepository<Department,Long> {

}
