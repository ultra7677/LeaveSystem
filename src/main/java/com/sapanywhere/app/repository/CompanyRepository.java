package com.sapanywhere.app.repository;

import org.springframework.data.repository.CrudRepository;
import com.sapanywhere.app.entity.Company;

public interface CompanyRepository extends CrudRepository<Company,Long> {

}
