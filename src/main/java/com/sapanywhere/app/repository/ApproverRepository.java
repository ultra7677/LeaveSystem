package com.sapanywhere.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.sapanywhere.app.entity.Leave;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.entity.Approver;

public interface ApproverRepository extends CrudRepository<Approver, Long>{
	Approver findByUser(User user);
	List<Approver> findByApprover(User approver);
	Page<Leave> findAllByApprover(User approver,Pageable pageable);
}
