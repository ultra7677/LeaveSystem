package com.sapanywhere.app.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.Leave;
import com.sapanywhere.app.entity.LeaveType;
import com.sapanywhere.app.entity.User;
import com.sapanywhere.app.enums.LeaveStatus;
import com.sapanywhere.app.exception.BusinessException;
import com.sapanywhere.app.model.LeaveRequestForm;
import com.sapanywhere.app.repository.LeaveRepository;

@Service
public class LeaveService {

	private static Logger logger = Logger.getLogger(LeaveService.class);

	@Autowired
	private LeaveRepository leaveRepository;

	@Autowired
	private ApproverService approverService;

	@Autowired
	private WorkHoursService workHoursService;
	
	@Autowired
	private LeaveTypeRuleService leaveTypeRuleService;
	
	@Autowired
	private MailService mailService;

	public void create(LeaveRequestForm form, User user) throws BusinessException {
		Leave leave = form.parse();
		leave.setUser(user);
		leave.setCreateTime(LocalDate.now());
		leave.setUpdateTime(LocalDate.now());
		leave.setYear(LocalDate.now().getYear());
		leave.setTotal(this.workHoursService.calcOffset(leave.getStartDate(),
				leave.getEndDate(), leave.getStartTime(), leave.getEndTime()));
		this.onValid(leave, user);
		this.leaveRepository.save(leave);
		this.mailService.sendLeaveRequestMail(leave);
	}

	public void update(Leave leave) {
		leave.setUpdateTime(LocalDate.now());
		this.leaveRepository.save(leave);
	}

	public void cancel(Long id, User owner) throws Exception {
		Leave leave = this.leaveRepository.findOne(id);
		if (leave != null) {
			if (leave.getUser().getId() == owner.getId()) {
				if (leave.getStatus() == LeaveStatus.NEW) {
					leave.setStatus(LeaveStatus.CANCEL);
					this.update(leave);
				} else {
					logger.error("Leave request has been dealed, then cann't cancel this leave request.");
					throw new Exception();
				}
			} else {
				logger.error("You cann't cancel other user's leave request");
				throw new Exception();
			}
		} else {
			logger.error("Cann't find this request.");
			throw new Exception();
		}
	}

	public void approve(Long id, User approverUser) throws Exception {
		this.deal(id, approverUser, LeaveStatus.CLOSE);
	}

	public void reject(Long id, User approverUser) throws Exception {
		this.deal(id, approverUser, LeaveStatus.REJECT);
	}

	public List<Leave> findLeavesByUser(User user) {
		return this.leaveRepository.findLeavesByUser(user);
	}
	
	public Page<Leave> findLeavesByUser(User user,Integer pageIndex) {
		return this.leaveRepository.findAllByUser(user,new PageRequest(pageIndex,10));
	}

	public int getTotalLeaveHours(User user, LeaveType leaveType) {
		Long result = this.leaveRepository.getTotalLeaveHours(user, leaveType);
		if (result == null) {
			return 0;
		}

		return result.intValue();
	}

	private void deal(Long id, User approverUser, LeaveStatus status)
			throws Exception {
		Leave leave = this.leaveRepository.findOne(id);

		if (leave != null) {
			User approver = this.approverService.getApprover(leave.getUser());
			if (approver.getId() == approverUser.getId()) {
				if (leave.getStatus() == LeaveStatus.NEW) {
					leave.setStatus(status);
					this.leaveRepository.save(leave);
				} else {
					logger.error("Leave request has been dealed, then cann't cancel this leave request.");
					throw new Exception();
				}
			} else {
				logger.error("You have no permission to deal with this leave request");
				throw new Exception();
			}
		} else {
			logger.error("Cann't find this request.");
			throw new Exception();
		}
	}

	private void onValid(Leave leave, User user) throws BusinessException {
		if (leave.getTotal() == 0) {
			logger.error("Leave request isn't contain work hours.");
			throw new BusinessException("error.leave.request.no.work.time");
		}

		for (Leave myLeave : this.findLeavesByUser(user)) {
			if ((myLeave.getStatus() == LeaveStatus.CLOSE || myLeave.getStatus() == LeaveStatus.NEW) && this.hasDuplicateTime(myLeave, leave)) {
				logger.error("Leave request has duplicate time with other leave requests.");
				throw new BusinessException("error.leave.request.duplicate.time");
			}
		}
		/*
		int totalLeaveHours = getTotalLeaveHours(user,leave.getType());
		if (totalLeaveHours + leave.getTotal() > this.leaveTypeRuleService.findById(leave.getType().getId()).getBaseDays() * workHoursService.getWorkHoursPerDay()){
			logger.error("Leave request's total time has exceeded available amount.");
			throw new BusinessException("error.leave.request.exceed.time");
		}*/
	}

	private boolean hasDuplicateTime(Leave sourceLeave, Leave targetLeave)
			throws BusinessException {
		boolean isAfterEndDate = targetLeave.getStartDateTime().isAfter(
				sourceLeave.getEndDateTime())
				|| targetLeave.getStartDateTime().isEqual(
						sourceLeave.getEndDateTime());
		boolean isBeforeStartDate = targetLeave.getEndDateTime().isBefore(
				sourceLeave.getStartDateTime())
				|| targetLeave.getEndDateTime().isEqual(
						sourceLeave.getStartDateTime());

		if (isAfterEndDate || isBeforeStartDate) {
			return false;
		}

		boolean inDateRange = this.inDateRange(sourceLeave, targetLeave);
		if (inDateRange) {
			return true;
		}

		boolean isAfterStartDate = targetLeave.getStartDateTime().isAfter(
				sourceLeave.getStartDateTime())
				|| targetLeave.getStartDateTime().isEqual(
						sourceLeave.getStartDateTime());

		if (isAfterStartDate) {
			int workHoursLength = this.workHoursService.calcOffset(
					targetLeave.getStartDate(), sourceLeave.getEndDate(),
					targetLeave.getStartTime(), sourceLeave.getEndTime());
			return workHoursLength != 0;
		}

		int workHoursLength = this.workHoursService.calcOffset(
				sourceLeave.getStartDate(), targetLeave.getEndDate(),
				sourceLeave.getStartTime(), targetLeave.getEndTime());
		return workHoursLength != 0;
	}

	private boolean inDateRange(Leave sourceLeave, Leave targetLeave) {
		boolean isAfter = targetLeave.getStartDateTime().isAfter(
				sourceLeave.getStartDateTime())
				|| targetLeave.getStartDateTime().isEqual(
						sourceLeave.getStartDateTime());
		boolean isBefore = targetLeave.getEndDateTime().isBefore(
				sourceLeave.getEndDateTime())
				|| targetLeave.getEndDateTime().isEqual(
						sourceLeave.getEndDateTime());
		return isAfter && isBefore;
	}
}
