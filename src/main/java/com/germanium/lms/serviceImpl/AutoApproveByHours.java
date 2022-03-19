package com.germanium.lms.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import com.germanium.lms.repository.ILeaveHistoryRepository;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.service.decorator.AutoApproveDecorator;
import com.germanium.lms.service.decorator.IAutoApprove;

public class AutoApproveByHours extends AutoApproveDecorator {

	private ILeaveHistoryRepository leaveHistRepo;

	@Value("${user.service.url}")
	private String userService = "http://localhost:8081";

	public AutoApproveByHours(IAutoApprove decoratedRule, ILeaveHistoryRepository leaveHistRepo) {
		super(decoratedRule);
		this.leaveHistRepo = leaveHistRepo;
	}

	@Override
	public String checkApprovalRule(Leave leaveRequest, String prev) {
		int depId = leaveRequest.getDepartmentId();
		float hrs = super.getParamRequired(userService, "/workHours", depId);
		return super.runHoursRule(leaveRequest, leaveHistRepo, 8, hrs, prev);
	}

}