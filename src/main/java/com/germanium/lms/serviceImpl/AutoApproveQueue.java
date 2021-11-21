package com.germanium.lms.serviceImpl;

import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.service.decorator.IAutoApprove;

public class AutoApproveQueue implements IAutoApprove {

	@Override
	public String checkApprovalRule(Leave leaveRequest) {
		int depId = leaveRequest.getDepartmentId();
		float workHours = 40*depId;
		return "queue";
	}

}
