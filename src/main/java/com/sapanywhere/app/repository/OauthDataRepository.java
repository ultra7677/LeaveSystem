package com.sapanywhere.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sapanywhere.app.entity.OauthData;

public interface OauthDataRepository extends CrudRepository<OauthData, Long>{

	List<OauthData> findByUserEmail(String userEmail);
}
