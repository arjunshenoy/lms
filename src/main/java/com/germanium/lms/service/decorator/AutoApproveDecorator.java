package com.germanium.lms.service.decorator;

import org.springframework.stereotype.Service;

import com.germanium.lms.model.factory.Leave;

public abstract class AutoApproveDecorator implements IAutoApprove {
	protected IAutoApprove decoratedRule;
	
	public AutoApproveDecorator(IAutoApprove decoratedRule) {
		this.decoratedRule = decoratedRule;
	}

	public String getRejectOrQueue(Leave leaveRequest, String prev) {
		String check = decoratedRule.checkApprovalRule(leaveRequest, prev);
		return check;
	}
}