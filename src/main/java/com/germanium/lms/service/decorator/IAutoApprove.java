package com.germanium.lms.service.decorator;

import com.germanium.lms.model.factory.Leave;

public interface IAutoApprove {
	String checkApprovalRule(Leave leaveRequest);
}
