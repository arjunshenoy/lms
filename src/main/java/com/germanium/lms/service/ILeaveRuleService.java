package com.germanium.lms.service;

public interface ILeaveRuleService {
	public boolean checkLeaveTypeRequestedForUserId(String leaveType, Integer userId) throws Exception;
}
