package com.germanium.lms.serviceImpl;

import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.service.decorator.AutoApproveDecorator;
import com.germanium.lms.service.decorator.IAutoApprove;

import org.springframework.beans.factory.annotation.Value;
import com.germanium.lms.repository.ILeaveHistoryRepository;

public class AutoApproveByEmployeeNumber extends AutoApproveDecorator {
	ILeaveHistoryRepository leaveHistRepo;

	@Value("${user.service.url}")
	private String userService = "http://user-service:8081";

	public AutoApproveByEmployeeNumber(IAutoApprove decoratedRule, ILeaveHistoryRepository leaveHistRepo) {
		super(decoratedRule);
		this.leaveHistRepo = leaveHistRepo;
	}

	@Override
	public String checkApprovalRule(Leave leaveRequest, String prev) {
		int depId = leaveRequest.getDepartmentId();
		float hrs = super.getParamRequired(userService, "/workEmployees", depId);
		return super.runHoursRule(leaveRequest, leaveHistRepo, 1, hrs, prev);
	}

}
