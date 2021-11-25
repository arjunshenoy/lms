package com.germanium.lms.service.decorator;

import org.springframework.stereotype.Service;

import com.germanium.lms.model.factory.Leave;

public interface IAutoApprove {
	String checkApprovalRule(Leave leaveRequest, String prev);
}
