package com.germanium.lms.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.repository.ILeaveRulesRepository;
import com.germanium.lms.repository.ILeaveStatisticsRepository;
import com.germanium.lms.service.ILeaveRuleService;

@Service
public class LeaveRuleServiceImpl implements ILeaveRuleService{
	@Autowired
	ILeaveRulesRepository leaveRulesRepo;

	@Autowired
	ILeaveStatisticsRepository leaveStatsRepo;

	public boolean checkLeaveTypeRequestedForUserId(int leaveId, Integer userId) throws Exception {
		LeaveRules leaveDetails = leaveRulesRepo.findById(leaveId).get();
		if (leaveDetails == null) {
			throw new Exception("Leave with leave Id :" + leaveId + " not found");
		}
		LeaveStats leaveStats = leaveStatsRepo.findLeaveTypeByUserIdAndLeaveId(leaveDetails.getLeaveId(), userId);
		if (leaveStats == null) {
			return false;
		} else {
			return true;
		}
	}
}
