package com.germanium.lms.service;

import java.util.List;

public interface ILeaveRuleService {
	
	public boolean checkLeaveTypeRequestedForUserId(int leaveId, Integer userId) ;
	
	public List<Integer> getUserForRuleCondition(String ruleExpression);
	
}
