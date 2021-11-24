package com.germanium.lms.serviceImpl;

import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.service.decorator.AutoApproveDecorator;
import com.germanium.lms.service.decorator.IAutoApprove;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.repository.ILeaveHistoryRepository;

public class AutoApproveByEmployeeNumber extends AutoApproveDecorator{
	ILeaveHistoryRepository leaveHistRepo;
	
	@Value("${user.service.url}")
	private String userService;
	
	public AutoApproveByEmployeeNumber(IAutoApprove decoratedRule, ILeaveHistoryRepository leaveHistRepo) {
		super(decoratedRule);
		this.leaveHistRepo = leaveHistRepo;
	}

	@Override
	public String checkApprovalRule(Leave leaveRequest, String prev) {
		int depId = leaveRequest.getDepartmentId();
		float hrs = super.getParamRequired(userService, "/workEmployees", depId);
		return super.runHoursRule(leaveRequest, 1, hrs, prev);
	}

	@Override
	protected List<LeaveHistory> getClashLeaves(Date current) {
		return leaveHistRepo.findClashingLeaves(current);
	}
	
}
