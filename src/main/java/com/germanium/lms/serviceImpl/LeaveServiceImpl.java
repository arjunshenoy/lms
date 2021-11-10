package com.germanium.lms.serviceImpl;

import java.util.List;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.germanium.lms.exception.ResourceNotFoundException;
import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.LeaveStatsId;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.repository.IActiveLeaveRepository;
import com.germanium.lms.repository.ILeaveHistoryRepository;
import com.germanium.lms.repository.ILeaveRulesRepository;
import com.germanium.lms.repository.ILeaveStatisticsRepository;
import com.germanium.lms.service.ILeaveService;
import com.germanium.lms.utils.LeaveHelper;

@Service
public class LeaveServiceImpl implements ILeaveService {

	Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);

	@Autowired
	ILeaveRulesRepository leaveRulesRepo;

	@Autowired
	ILeaveStatisticsRepository leaveStatsRepo;

	@Autowired
	IActiveLeaveRepository activeLeaveRepo;

	@Autowired
	ILeaveHistoryRepository leaveHistoryRepo;

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
	}

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

	@Override
	public ActiveLeaves createLeaveRequest(Leave leaveRequest) throws Exception {
		LeaveStatsId statsId = new LeaveStatsId();
		statsId.setEmployeeId(leaveRequest.getEmployeeId());
		statsId.setLeaveId(leaveRequest.getLeaveId());
		Optional<LeaveStats> leaveStats = leaveStatsRepo.findById(statsId);
		if (!leaveStats.isPresent()) {
			throw new Exception("No data found in stats table for user" + leaveRequest.getEmployeeId());
		}
		long diffInMillies = Math.abs(leaveRequest.getToDate().getTime() - leaveRequest.getFromDate().getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		if (leaveStats.get().getLeaveCount() - diff < 0) {
			logger.info("User Dosent Have enough Leaves");
			throw new Exception("User Dosent Have enough Leaves");
		}
		leaveStats.get().setLeaveCount(leaveStats.get().getLeaveCount() - diff);
		ActiveLeaves savedLeave = activeLeaveRepo.save(LeaveHelper.dtoToModelMapper(leaveRequest));
		leaveStatsRepo.save(leaveStats.get());

		return savedLeave;
	}

	@Override
	public ActiveLeaves getActiveLeavesById(Integer leaveId) {
		Optional<ActiveLeaves> optionalLeave = activeLeaveRepo.findById(leaveId);
		if (optionalLeave.isEmpty()) {
			throw new ResourceNotFoundException("Leave Request with id: not found" + leaveId);
		}
		return optionalLeave.get();
	}

	@Override
	public Boolean takeLeaveDecision(Integer leaveRequestId, String decision) throws Exception {

		Optional<ActiveLeaves> optionalLeave = activeLeaveRepo.findById(leaveRequestId);
		if (optionalLeave.isEmpty()) {
			throw new ResourceNotFoundException("Leave Request with id: not found" + leaveRequestId);
		}

		LeaveHistory leaveHistory = LeaveHelper.copyActiveToHistory(optionalLeave.get());

		if (decision.equals("approve")) {
			leaveHistory.setLeaveStatus("APPROVED");
		} else {
			leaveHistory.setLeaveStatus("REJECTED");
		}

		LeaveHistory savedHistory = leaveHistoryRepo.save(leaveHistory);

		if (optionalLeave.get().getLeaveRequestId() != savedHistory.getLeaveHistoryId().getLeaveRequestId()) {
			logger.error("Failed to add leave to hsitory table" + leaveRequestId);
			throw new Exception("Failed to add leave to history table" + leaveRequestId);
		} else {
			activeLeaveRepo.deleteById(leaveRequestId);
		}

		if (savedHistory.getLeaveStatus().equals("REJECTED")) {
			long diffInMillies = Math
					.abs(optionalLeave.get().getToDate().getTime() - optionalLeave.get().getFromDate().getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			LeaveStatsId statsId = new LeaveStatsId();
			statsId.setEmployeeId(optionalLeave.get().getEmployeeId());
			statsId.setLeaveId(optionalLeave.get().getLeaveId());
			Optional<LeaveStats> leaveStats = leaveStatsRepo.findById(statsId);
			if (!leaveStats.isPresent()) {
				throw new Exception("No data found in stats table for user" + optionalLeave.get().getEmployeeId());
			}
			leaveStats.get().setLeaveCount(leaveStats.get().getLeaveCount() + diff);
			leaveStatsRepo.save(leaveStats.get());

		}

		return true;

	}

}
