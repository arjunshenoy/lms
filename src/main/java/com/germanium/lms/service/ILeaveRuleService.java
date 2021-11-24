package com.germanium.lms.service;

import java.util.List;

import com.germanium.lms.model.LeaveRules;

public interface ILeaveRuleService {
	
	public boolean checkLeaveTypeRequestedForUserId(int leaveId, Integer userId) ;
	
	public List<Integer> getUserForRuleCondition(String ruleExpression);
	
}
