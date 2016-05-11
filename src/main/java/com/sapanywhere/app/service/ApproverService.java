package com.sapanywhere.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.Leave;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.entity.Approver;
import com.sapanywhere.app.repository.LeaveRepository;
import com.sapanywhere.app.repository.ApproverRepository;
import com.sapanywhere.app.repository.UserRepository;

@Service
public class ApproverService {

	private final int PageSize = 10;
	
	@Autowired
	private ApproverRepository approverRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LeaveRepository leaveRepository;

	public void create(User user) {
		Approver approver = new Approver();
		approver.setUser(user);
		this.approverRepository.save(approver);
	}

	public void update(long id, long approverId) throws Exception {
		User user = this.userRepository.findOne(approverId);
		if (user == null) {
			throw new Exception();
		}

		Approver approver = this.approverRepository.findOne(id);
		if (approver == null) {
			throw new Exception();
		}

		approver.setApprover(user);
		this.approverRepository.save(approver);
	}

	public User getApprover(User user) {
		Approver approver = this.approverRepository.findByUser(user);
		if (approver != null) {
			return approver.getApprover();
		}

		return null;
	}
	
	public Page<Leave> findLeaves(User approver,int pageIndex){
		PageRequest request = new PageRequest(pageIndex,PageSize);
		return this.leaveRepository.findWaitApproveLeaves(approver,request);
	}

	public Iterable<Approver> findAllApproves() {
		return this.approverRepository.findAll();
	}
}
