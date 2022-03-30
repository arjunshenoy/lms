package com.germanium.lms.serviceImpl;

import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.service.decorator.IAutoApprove;

public class AutoApproveCache implements IAutoApprove{

	@Override
	public String checkApprovalRule(Leave leaveRequest, String prev) {
		return "queue";
	}

}
