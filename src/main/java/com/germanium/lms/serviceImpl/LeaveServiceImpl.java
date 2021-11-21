package com.germanium.lms.serviceImpl;

import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.exception.ResourceNotFoundException;
import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.LeaveStatsId;
import com.germanium.lms.model.dto.MailRequestDto;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.repository.IActiveLeaveRepository;
import com.germanium.lms.repository.ILeaveHistoryRepository;
import com.germanium.lms.repository.ILeaveRulesRepository;
import com.germanium.lms.repository.ILeaveStatisticsRepository;
import com.germanium.lms.service.ILeaveRuleService;
import com.germanium.lms.service.ILeaveService;
import com.germanium.lms.utils.LeaveHelper;

@Service
public class LeaveServiceImpl implements ILeaveService {

	Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);

	private static final String NOTIFY_EMAIL_ENDPOINT = "/mail/leave/notify";

	@Value("${user.service.url}")
	private String userService;

	@Autowired
	ILeaveRulesRepository leaveRulesRepo;

	@Autowired
	ILeaveStatisticsRepository leaveStatsRepo;

	@Autowired
	IActiveLeaveRepository activeLeaveRepo;

	@Autowired
	ILeaveHistoryRepository leaveHistoryRepo;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	ILeaveRuleService leaveRuleService;

	@Override
	public List<LeaveRules> getLeaveRules() {
		return (List<LeaveRules>) leaveRulesRepo.findAll();
	}

	@Override
	public LeaveRules findLeavesById(Integer leaveId) throws ResourceNotFoundException {
		Optional<LeaveRules> optionalLeave = leaveRulesRepo.findById(leaveId);
		if (!optionalLeave.isPresent()) {
			throw new ResourceNotFoundException("Leave With Leave Id: Not Found " + leaveId);
		}
		return optionalLeave.get();
	}

	@Override
	public List<LeaveRules> createLeaveRules(List<LeaveRules> leaveType) {
		logger.info("Saving details into repository");
		return (List<LeaveRules>) leaveRulesRepo.saveAll(leaveType);
	}

	@Override
	public LeaveRules updateLeaveRules(Integer leaveId, LeaveRules leaveRule) throws ResourceNotFoundException {

		if (!leaveRulesRepo.existsById(leaveId)) {
			throw new ResourceNotFoundException("Leave With Leave Id: Not Found " + leaveId);
		}
		logger.info("Updating Leave Details");
		return leaveRulesRepo.save(leaveRule);
	}

	@Override
	public boolean deleteLeaveRules(Integer leaveId) throws ResourceNotFoundException {
		logger.info("Deleting Leave Details");
		if (!leaveRulesRepo.existsById(leaveId)) {
			throw new ResourceNotFoundException("Leave With Leave Id: Not Found " + leaveId);
		}
		leaveRulesRepo.deleteById(leaveId);
		logger.info("Successfully deleted leave with ID: {}", leaveId);
		return true;
	}

	@Override
	public List<LeaveStats> getLeaveStatsById(Integer employeeId) {
		logger.info("Retrieving Leave stats Details");
		return leaveStatsRepo.findByEmployeeId(employeeId);
	}

	@Override
	public void addLeaveStatsForNewUsers(Integer userId) {
		logger.info("Creating Leave Statistics for User Id: {}", userId);

		List<LeaveRules> leaveRules = (List<LeaveRules>) leaveRulesRepo.findAll();
		List<LeaveStats> leaveStatsList = new ArrayList<>();
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
		logger.info("Rule statistics creation done successfully {}", userId);

	}

	@Override
	public ActiveLeaves createLeaveRequest(Leave leaveRequest) throws Exception {
		LeaveStatsId statsId = new LeaveStatsId();
		statsId.setEmployeeId(leaveRequest.getEmployeeId());
		statsId.setLeaveId(leaveRequest.getLeaveId());
		Boolean result = leaveRuleService.checkLeaveTypeRequestedForUserId(leaveRequest.getLeaveId(),leaveRequest.getEmployeeId());
		if (Boolean.FALSE.equals(result)) {
			logger.info("User does not have the leave type requested");
			throw new ResourceNotFoundException("User does not have the leave type requested");
		}
		Optional<LeaveStats> leaveStats = leaveStatsRepo.findById(statsId);
		if (!leaveStats.isPresent()) {
			throw new ResourceNotFoundException("No data found in stats table for user" + leaveRequest.getEmployeeId());
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

		MailRequestDto mailRequest = new MailRequestDto();
		mailRequest.setContent(
				"Leave Application by User Id : " + leaveRequest.getEmployeeId() + " submitted successfully \n"
						+ "Leave Details: \n" + "Leave type: " + leaveRequest.getLeaveId() + "\n Leave Start Date: "
						+ leaveRequest.getFromDate() + "\n Leave End Date: " + leaveRequest.getToDate());
		mailRequest.setSubject(
				"Leave Application by User Id : " + leaveRequest.getEmployeeId() + " submitted successfully");
		mailRequest.setUserId(leaveRequest.getEmployeeId());

		restTemplate.postForObject(new StringBuilder(userService).append(NOTIFY_EMAIL_ENDPOINT).toString(), mailRequest,
				MailRequestDto.class);

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
			logger.error("Failed to add leave to hsitory table {}", leaveRequestId);
			throw new Exception("Failed to add leave to history table" + leaveRequestId);
		} else {
			activeLeaveRepo.deleteById(leaveRequestId);
		}

		if (savedHistory.getLeaveStatus().equals("REJECTED")) {
			incrementLeaveCount(optionalLeave.get().getFromDate(), optionalLeave.get().getToDate(),
					optionalLeave.get().getEmployeeId(), optionalLeave.get().getLeaveId());
		}

		return true;

	}

	@Override
	public Boolean cancelWithdrawLeave(Integer leaveRequestId, String cancelDecision) {
		
		
		if (cancelDecision.equalsIgnoreCase("Cancel")) {
			Optional<LeaveHistory> optionalLeaveHistory = leaveHistoryRepo
					.findByLeaveHistoryIdLeaveRequestId(leaveRequestId);
			if (!optionalLeaveHistory.isPresent()) {
				logger.error("No data found in Leave History table for Leave Request Id {}", leaveRequestId);
				throw new ResourceNotFoundException("No data found in Leave History table for Leave Request Id" + leaveRequestId);
			}
			optionalLeaveHistory.get().setLeaveStatus("CANCELLED");
			leaveHistoryRepo.save(optionalLeaveHistory.get());
			incrementLeaveCount(optionalLeaveHistory.get().getFromDate(), optionalLeaveHistory.get().getToDate(),
					optionalLeaveHistory.get().getLeaveHistoryId().getEmployeeId(),
					optionalLeaveHistory.get().getLeaveId());

		}
		if (cancelDecision.equalsIgnoreCase("Withdraw")) {
			Optional<ActiveLeaves> optionalLeave = activeLeaveRepo.findById(leaveRequestId);
			if (!optionalLeave.isPresent()) {
				logger.error("No data found in Active Leave table for Leave Request Id {}", leaveRequestId);
				throw new ResourceNotFoundException("No data found in Active Leave table for Leave Request Id" + leaveRequestId);
			}
			LeaveHistory leaveHistory = LeaveHelper.copyActiveToHistory(optionalLeave.get());
			activeLeaveRepo.deleteById(leaveRequestId);
			leaveHistory.setLeaveStatus("CANCELLED");
			leaveHistoryRepo.save(leaveHistory);
			incrementLeaveCount(optionalLeave.get().getFromDate(), optionalLeave.get().getToDate(),
					optionalLeave.get().getEmployeeId(), optionalLeave.get().getLeaveId());
		}
		return true;
	}

	public boolean incrementLeaveCount(Date fromDate, Date toDate, int employeeId, int leaveId) {
		long diffInMillies = Math.abs(toDate.getTime() - fromDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		LeaveStatsId statsId = new LeaveStatsId();
		statsId.setEmployeeId(employeeId);
		statsId.setLeaveId(leaveId);
		Optional<LeaveStats> leaveStats = leaveStatsRepo.findById(statsId);
		if (!leaveStats.isPresent()) {
			logger.error("No datafound in stats table for userId {}",employeeId);
			throw new ResourceNotFoundException("No data found in stats table for user" + employeeId);
		}
		leaveStats.get().setLeaveCount(leaveStats.get().getLeaveCount() + diff);
		leaveStatsRepo.save(leaveStats.get());
		return true;
	}

}
