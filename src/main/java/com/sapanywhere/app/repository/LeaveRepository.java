package com.sapanywhere.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sapanywhere.app.entity.Leave;
import com.sapanywhere.app.entity.LeaveType;
import com.sapanywhere.app.entity.User;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
	
	@Query("select leave from Leave leave where leave.user = ?1 and status = 0")
	public List<Leave> findAllNewLeavesByUser(User user);
	
	public Page<Leave> findAllByUser(User user,Pageable pageable);
	
	@Query("select t0 from Leave t0  where t0.status = 0 and t0.user in (select t1.user from Approver t1 where t1.approver = ?1)")
	public Page<Leave> findWaitApproveLeaves(User user,Pageable pageable);
	
	public List<Leave> findLeavesByUser(User user);
	
	@Query("SELECT SUM(total) FROM Leave leave WHERE leave.user = ?1 and leave.type = ?2 and status = 1")
	public Long getTotalLeaveHours(User user,LeaveType type);
}
