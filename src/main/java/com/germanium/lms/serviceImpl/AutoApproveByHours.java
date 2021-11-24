package com.germanium.lms.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.repository.ILeaveHistoryRepository;
import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.service.decorator.AutoApproveDecorator;
import com.germanium.lms.service.decorator.IAutoApprove;

public class AutoApproveByHours extends AutoApproveDecorator{
	private ILeaveHistoryRepository leaveHistRepo;
	@Value("${user.service.url}")
	private String userService;

	public AutoApproveByHours(IAutoApprove decoratedRule, ILeaveHistoryRepository leaveHistRepo) {
		super(decoratedRule);
		this.leaveHistRepo = leaveHistRepo;
	}
	
	@Override
	public String checkApprovalRule(Leave leaveRequest, String prev) {
		int depId = leaveRequest.getDepartmentId();
		float hrs = super.getParamRequired(userService, "/workHours", depId);
		return super.runHoursRule(leaveRequest, 8, hrs, prev);
	}

	@Override
	protected List<LeaveHistory> getClashLeaves(Date current) {
		return leaveHistRepo.findClashingLeaves(current);
	}	
}