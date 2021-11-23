package com.germanium.lms.service;

import java.util.List;

import com.germanium.lms.exception.ResourceNotFoundException;
import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.factory.Leave;

public interface ILeaveService {

	public List<LeaveRules> getLeaveRules();

	public LeaveRules findLeavesById(Integer leaveId) throws ResourceNotFoundException;

	public List<LeaveRules> createLeaveRules(List<LeaveRules> leaveType);

	public LeaveRules updateLeaveRules(Integer leaveId, LeaveRules leaveRule) throws ResourceNotFoundException;

	public boolean deleteLeaveRules(Integer leaveId) throws ResourceNotFoundException;

	public boolean addLeaveStatsForNewUsers(Integer userId);

	public List<LeaveStats> getLeaveStatsById(Integer employeeId);

	public ActiveLeaves createLeaveRequest(Leave leaveRequest) throws Exception;

	public ActiveLeaves getActiveLeavesById(Integer leaveId);
	
	public Boolean takeLeaveDecision(Integer leaveId, String decision) throws Exception;
	
	public Boolean cancelWithdrawLeave(Integer leaveId, String cancelDecision);
}
