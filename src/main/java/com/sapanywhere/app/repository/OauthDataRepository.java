package com.sapanywhere.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sapanywhere.app.entity.OauthData;

public interface OauthDataRepository extends CrudRepository<OauthData, Long>{

	List<OauthData> findByUserEmail(String userEmail);
	
	List<OauthData> findByCompanyCodeAndUserCode(String companyCode,
			String userCode);

	@Modifying
	@Transactional
	@Query("delete from OauthData t0 where t0.companyCode = ?1")
	void deleteByCompanyCode(String companyCode);
}
