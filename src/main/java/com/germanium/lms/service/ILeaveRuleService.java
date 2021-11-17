package com.germanium.lms.service;

public interface ILeaveRuleService {
	
	public boolean checkLeaveTypeRequestedForUserId(int leaveId, Integer userId) throws Exception;
	
}
