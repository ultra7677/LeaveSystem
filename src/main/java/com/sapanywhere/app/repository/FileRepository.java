package com.sapanywhere.app.repository;

import org.springframework.data.repository.CrudRepository;
import com.sapanywhere.app.entity.Files;

public interface FileRepository extends CrudRepository<Files, Long>{
	
}
