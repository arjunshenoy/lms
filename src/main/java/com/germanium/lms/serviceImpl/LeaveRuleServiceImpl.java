package com.germanium.lms.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.repository.ILeaveRulesRepository;
import com.germanium.lms.repository.ILeaveStatisticsRepository;
import com.germanium.lms.service.ILeaveRuleService;

public class LeaveRuleServiceImpl implements ILeaveRuleService{
	@Autowired
	ILeaveRulesRepository leaveRulesRepo;

	@Autowired
	ILeaveStatisticsRepository leaveStatsRepo;

	public boolean checkLeaveTypeRequestedForUserId(String leaveType, Integer userId) throws Exception {
		LeaveRules leaveDetails = leaveRulesRepo.findByName(leaveType);
		if (leaveDetails == null) {
			throw new Exception("Leave with leave name :" + leaveType + " not found");
		}
		LeaveStats leaveStats = leaveStatsRepo.findLeaveTypeByUserIdAndLeaveId(leaveDetails.getLeaveId(), userId);
		if (leaveStats == null) {
			return false;
		} else {
			return true;
		}

	}

}
