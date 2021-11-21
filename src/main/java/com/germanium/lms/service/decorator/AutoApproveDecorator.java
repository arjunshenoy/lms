package com.germanium.lms.service.decorator;

import com.germanium.lms.model.factory.Leave;

public abstract class AutoApproveDecorator implements IAutoApprove {
	IAutoApprove decoratedRule;
	public abstract String checkApprovalRule(Leave leaveRequest);
}