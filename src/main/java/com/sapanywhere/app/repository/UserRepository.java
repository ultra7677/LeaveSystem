package com.sapanywhere.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.sapanywhere.app.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);
	
	User findBySapAccount(String sapAccount);
}
