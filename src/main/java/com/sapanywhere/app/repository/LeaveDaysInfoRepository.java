package com.sapanywhere.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapanywhere.app.entity.LeaveDaysInfo;
import com.sapanywhere.app.entity.User;

public interface LeaveDaysInfoRepository extends JpaRepository<LeaveDaysInfo, Long>{

	List<LeaveDaysInfo> findAllByUserAndYear(User user,int year);
}
