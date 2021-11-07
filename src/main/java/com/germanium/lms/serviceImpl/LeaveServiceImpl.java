package com.germanium.lms.serviceImpl;

import java.util.List;
import java.util.ArrayList;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.germanium.lms.models.LeaveRules;
import com.germanium.lms.models.LeaveStats;

import com.germanium.lms.models.LeaveStatsId;

import com.germanium.lms.repository.ILeaveRulesRepository;
import com.germanium.lms.repository.ILeaveStatisticsRepository;
import com.germanium.lms.service.ILeaveService;

@Service
public class LeaveServiceImpl implements ILeaveService {

	Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);

	@Autowired
	ILeaveRulesRepository leaveRulesRepo;
	
	@Autowired
	ILeaveStatisticsRepository leaveStatsRepo;
	

	@Autowired
	ILeaveStatisticsRepository leaveStatsRepo;

	@Override
	public List<LeaveRules> getLeaveRules() {
		return (List<LeaveRules>) leaveRulesRepo.findAll();
	}

	@Override
	public LeaveRules findLeavesById(Integer leaveId) throws Exception {
		Optional<LeaveRules> optionalLeave = leaveRulesRepo.findById(leaveId);
		if (!optionalLeave.isPresent()) {
			throw new Exception("Leave With Leave Id: Not Found " + leaveId);
		}
		return optionalLeave.get();
	}

	@Override
	public List<LeaveRules> createLeaveRules(List<LeaveRules> leaveType) {
		logger.info("Saving details into repository");
		return (List<LeaveRules>) leaveRulesRepo.saveAll(leaveType);
	}

	@Override
	public LeaveRules updateLeaveRules(Integer leaveId, LeaveRules leaveRule) throws Exception {

		if (!leaveRulesRepo.existsById(leaveId)) {
			throw new Exception("Leave With Leave Id: Not Found " + leaveId);
		}
		logger.info("Updating Leave Details");
		return leaveRulesRepo.save(leaveRule);
	}

	@Override
	public boolean deleteLeaveRules(Integer leaveId) throws Exception {
		logger.info("Deleting Leave Details");
		if (!leaveRulesRepo.existsById(leaveId)) {
			throw new Exception("Leave With Leave Id: Not Found " + leaveId);
		}
		leaveRulesRepo.deleteById(leaveId);
		logger.info("Successfully deleted leave with ID: {}", leaveId);
		return true;
	}

	@Override
	public List<LeaveStats> getLeaveStatsById(Integer employeeId) {
		logger.info("Retrieving Leave stats Details");
		List<LeaveStats> leaveStats = leaveStatsRepo.findByEmployeeId(employeeId);
		return leaveStats;
    
	@Override
	public void addLeaveStatsForNewUsers(Integer userId) {
		logger.info("Creating Leave Statistics for User Id:" + userId);

		List<LeaveRules> leaveRules = (List<LeaveRules>) leaveRulesRepo.findAll();
		List<LeaveStats> leaveStatsList = new ArrayList<LeaveStats>();
		leaveRules.stream().forEach(leave -> {
			LeaveStatsId id = new LeaveStatsId();
			id.setEmployeeId(userId);
			id.setLeaveId(leave.getLeaveId());
			LeaveStats ls = new LeaveStats();
			ls.setId(id);
			ls.setLeaveCount(leave.getLeaveCount());
			leaveStatsList.add(ls);
		});
		leaveStatsRepo.saveAll(leaveStatsList);
		logger.info("Rule statistics creation done successfully" + userId);


	}

}
