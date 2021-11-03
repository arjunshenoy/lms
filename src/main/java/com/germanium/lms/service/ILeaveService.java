package com.germanium.lms.service;

import java.util.List;

import com.germanium.lms.models.LeaveRules;


public interface ILeaveService {
	
	public List<LeaveRules> getLeaveRules();
	
	public LeaveRules findLeavesById (Integer leaveId) throws Exception;

	public List<LeaveRules> createLeaveRules(List<LeaveRules> leaveType);
	
	public LeaveRules updateLeaveRules(Integer leaveId,LeaveRules leaveRule) throws Exception;

	public boolean deleteLeaveRules(Integer leaveId) throws Exception;

	
}
