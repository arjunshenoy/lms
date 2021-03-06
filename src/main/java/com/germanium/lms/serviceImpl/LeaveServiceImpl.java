package com.germanium.lms.serviceImpl;

import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.exception.LeaveServiceException;
import com.germanium.lms.exception.ResourceNotFoundException;
import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.LeaveStatsId;
import com.germanium.lms.model.dto.Department;
import com.germanium.lms.model.dto.Manager;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.repository.IActiveLeaveRepository;
import com.germanium.lms.repository.ILeaveHistoryRepository;
import com.germanium.lms.repository.ILeaveRulesRepository;
import com.germanium.lms.repository.ILeaveStatisticsRepository;
import com.germanium.lms.service.ILeaveRuleService;
import com.germanium.lms.service.ILeaveService;
import com.germanium.lms.service.adapter.ITarget;
import com.germanium.lms.service.iterator.Iterator;
import com.germanium.lms.service.lazy.ManagerList;
import com.germanium.lms.service.lazy.ManagerListProxyImpl;
import com.germanium.lms.service.memento.LeaveMemento;
import com.germanium.lms.service.memento.LeaveMementoCareTaker;
import com.germanium.lms.service.decorator.IAutoApprove;
import com.germanium.lms.utils.LeaveHelper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class LeaveServiceImpl implements ILeaveService {

	Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);

	private static final String NOTIFY_EMAIL_ENDPOINT = "/mail/leave/notify";
	private static final String LEAVE_APPLICATION = "Leave Application by User Id : ";
	private static final String USER_DETAILS_ENDPOINT = "/api/v1/user/profiles/query";


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
	
	IAutoApprove autoApproval =  new AutoApproveCache();  

	@Autowired
	ITarget target;

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
	@Transactional
	public LeaveRules createLeaveRules(LeaveRules leaveType) {
		logger.info("Saving details into repository");
		LeaveRules savedRule = leaveRulesRepo.save(leaveType);
		if (!savedRule.getRuleExpression().isEmpty()) {
			// Rule Expression is not empty so we should filter out the users who satisfy
			// the rule condition
			// A call will be made to the user service to get the list of users

			List<Integer> userIds = leaveRuleService.getUserForRuleCondition(savedRule.getRuleExpression());
			List<LeaveStats> leaveStatsForNewLeave = new ArrayList<>();
			userIds.stream().forEach(userId -> {
				LeaveStatsId id = new LeaveStatsId();
				id.setEmployeeId(userId);
				id.setLeaveId(savedRule.getLeaveId());
				LeaveStats ls = new LeaveStats();
				ls.setId(id);
				ls.setLeaveCount(savedRule.getLeaveCount());
				leaveStatsForNewLeave.add(ls);
			});
			leaveStatsRepo.saveAll(leaveStatsForNewLeave);
			logger.info("Rule statistics creation done successfully for new Rule {}", savedRule.getName());

		}
		return savedRule;
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
	public boolean addLeaveStatsForNewUsers(Integer userId) {
		logger.info("Creating Leave Statistics for User Id: {}", userId);
		LeaveRuleCollectionImpl leaveRuleObj = new LeaveRuleCollectionImpl(leaveRulesRepo);
		Iterator iterator = leaveRuleObj.getIterator();
		List<LeaveStats> leaveStatsList = new ArrayList<>();
		while (iterator.hasNext()) {
			LeaveRules leave = (LeaveRules) iterator.next();
			LeaveStatsId id = new LeaveStatsId();
			id.setEmployeeId(userId);
			id.setLeaveId(leave.getLeaveId());
			LeaveStats ls = new LeaveStats();
			ls.setId(id);
			ls.setLeaveCount(leave.getLeaveCount());
			leaveStatsList.add(ls);
		}
		leaveStatsRepo.saveAll(leaveStatsList);
		logger.info("Rule statistics creation done successfully {}", userId);
		return true;
	}
	
	@Override
	public void enableAutoApproval() {				
		// decorate/chain with each rule
		 autoApproval = new AutoApproveByEmployeeNumber(
				new AutoApproveByHours(new AutoApproveQueue(), leaveHistoryRepo), leaveHistoryRepo); 		
		
	}
	
	@Override
	public void disableAutoApproval() {				
			autoApproval = new AutoApproveCache();
			
	}
	
	@Override
	public String autoApproval(Leave leaveRequest) {
		return autoApproval.checkApprovalRule(leaveRequest, "approve");
	}

	@Override
	@Transactional
	public ActiveLeaves createLeaveRequest(Leave leaveRequest) {
		LeaveStatsId statsId = new LeaveStatsId();
		statsId.setEmployeeId(leaveRequest.getEmployeeId());
		statsId.setLeaveId(leaveRequest.getLeaveId());
		Boolean result = leaveRuleService.checkLeaveTypeRequestedForUserId(leaveRequest.getLeaveId(),
				leaveRequest.getEmployeeId());
		if (Boolean.FALSE.equals(result)) {
			logger.info("User does not have the leave type requested");
			throw new ResourceNotFoundException("User does not have the leave type requested");
		}
		Optional<LeaveStats> leaveStats = leaveStatsRepo.findById(statsId);
		if (!leaveStats.isPresent()) {
			throw new ResourceNotFoundException(
					"No data found in stats table for user " + leaveRequest.getEmployeeId());
		}
		long diffInMillies = Math.abs(leaveRequest.getToDate().getTime() - leaveRequest.getFromDate().getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
		if (leaveStats.get().getLeaveCount() - diff < 0) {
			logger.info("User does not have enough Leaves");
			throw new LeaveServiceException("User does not have enough Leaves");
		}
		leaveStats.get().setLeaveCount(leaveStats.get().getLeaveCount() - diff);
		ActiveLeaves savedLeave = activeLeaveRepo.save(LeaveHelper.dtoToModelMapper(leaveRequest));
		leaveStatsRepo.save(leaveStats.get());

		String subject = LEAVE_APPLICATION + leaveRequest.getEmployeeId() + " submitted successfully";
		(new NotifyLeaveRequest(subject, new Mailer(leaveRequest.getEmployeeId(), userService, NOTIFY_EMAIL_ENDPOINT, restTemplate), leaveRequest)).send();
		return savedLeave;
	}

	@Override
	public ActiveLeaves getActiveLeavesById(Integer leaveId) {
		Optional<ActiveLeaves> optionalLeave = activeLeaveRepo.findById(leaveId);
		if (optionalLeave.isEmpty()) {
			throw new ResourceNotFoundException("Leave Request with id: not found " + leaveId);
		}
		return optionalLeave.get();
	}

	@Override
	public Boolean takeLeaveDecision(Integer leaveRequestId, String decision) {

		Optional<ActiveLeaves> optionalLeave = activeLeaveRepo.findById(leaveRequestId);
		if (optionalLeave.isEmpty()) {
			throw new ResourceNotFoundException("Leave Request with id: not found " + leaveRequestId);
		}

		LeaveHistory leaveHistory = LeaveHelper.copyActiveToHistory(optionalLeave.get());
		Boolean response = false;
		try {
			// Finding approved leaves ending with date of yesterday
			LeaveHistory alreadyExistingLeaveHistory = leaveHistoryRepo
					.findByFromDateAndIsApproved(DateUtils.addDays(leaveHistory.getFromDate(), -1), "APPROVED");
			if (alreadyExistingLeaveHistory != null) {
				Optional<LeaveRules> existingLeaveRule = leaveRulesRepo
						.findById(alreadyExistingLeaveHistory.getLeaveId());
				if (existingLeaveRule.isEmpty())
					throw new ResourceNotFoundException("Leave rules not exist for leave ID : " + leaveRequestId);
				// Finding leave rules for the applied leave
				Optional<LeaveRules> appliedLeave = leaveRulesRepo.findById(optionalLeave.get().getLeaveId());
				if (appliedLeave.isPresent() && !existingLeaveRule.get().getCombinableLeaves().contains(appliedLeave.get().getName())) {
					logger.warn("Leaves {} and {} can not be combined.", existingLeaveRule.get().getName(),
							appliedLeave.get().getName());
					response = setDecision(leaveHistory, optionalLeave, leaveRequestId, "REJECTED");
				} else {
					response = setDecision(leaveHistory, optionalLeave, leaveRequestId, decision);
				}
			}
			response = setDecision(leaveHistory, optionalLeave, leaveRequestId, decision);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return response;
	}

	public Boolean setDecision(LeaveHistory leaveHistory, Optional<ActiveLeaves> optionalLeave, Integer leaveRequestId,
			String decision) throws Exception {
		if (optionalLeave.isPresent()) {
			if (decision.equals("approve")) {
				leaveHistory.setLeaveStatus("APPROVED");
				optionalLeave.get().setLeaveStatus("APPROVED");
			} else {
				leaveHistory.setLeaveStatus("REJECTED");
				optionalLeave.get().setLeaveStatus("REJECTED");
			}
		}

		LeaveHistory savedHistory = leaveHistoryRepo.save(leaveHistory);

		if (optionalLeave.get().getLeaveRequestId() != savedHistory.getLeaveHistoryId().getLeaveRequestId()) {
			logger.error("Failed to add leave to hsitory table {}", leaveRequestId);
			throw new LeaveServiceException("Failed to add leave to history table " + leaveRequestId);
		} else {
			activeLeaveRepo.deleteById(leaveRequestId);
		}

		if (savedHistory.getLeaveStatus().equals("REJECTED")) {
			incrementLeaveCount(optionalLeave.get().getFromDate(), optionalLeave.get().getToDate(),
					optionalLeave.get().getEmployeeId(), optionalLeave.get().getLeaveId());
		}

		/* Memento Design Pattern Start */
		LeaveMementoCareTaker leaveMementoCareTaker = new LeaveMementoCareTaker();

		leaveMementoCareTaker.addMementoToCache(optionalLeave.get(), decision);
		/* Memento Design Pattern End */

		String subject = "Leave Application Decision for Leave Request ID :" + optionalLeave.get().getLeaveRequestId();
		(new NotifyLeaveHistory(subject, new Mailer(optionalLeave.get().getEmployeeId(), userService, NOTIFY_EMAIL_ENDPOINT, restTemplate), optionalLeave, null, leaveHistory.getLeaveStatus())).send();
		return true;

	}

	@Override
	public Boolean cancelWithdrawLeave(Integer leaveRequestId, String cancelDecision) {

		if (cancelDecision.equalsIgnoreCase("Cancel")) {
			Optional<LeaveHistory> optionalLeaveHistory = leaveHistoryRepo
					.findByLeaveHistoryIdLeaveRequestId(leaveRequestId);
			if (!optionalLeaveHistory.isPresent()) {
				logger.error("No data found in Leave History table for Leave Request Id {}", leaveRequestId);
				throw new ResourceNotFoundException(
						"No data found in Leave History table for Leave Request Id " + leaveRequestId);
			}

			optionalLeaveHistory.get().setLeaveStatus("CANCELLED");
			leaveHistoryRepo.save(optionalLeaveHistory.get());
			incrementLeaveCount(optionalLeaveHistory.get().getFromDate(), optionalLeaveHistory.get().getToDate(),
					optionalLeaveHistory.get().getLeaveHistoryId().getEmployeeId(),
					optionalLeaveHistory.get().getLeaveId());

			if (checkForPendingLeaveApproval(optionalLeaveHistory.get().getFromDate(),
					optionalLeaveHistory.get().getToDate(), optionalLeaveHistory.get().getDepartmentId())) {
				logger.info("Approved one pending leave");
			}

			int id = optionalLeaveHistory.get().getLeaveHistoryId().getEmployeeId();
			String subject = "Leave Application Cancelled for User Id : "+ id;
			(new NotifyLeaveHistory(subject, new Mailer(id, userService, NOTIFY_EMAIL_ENDPOINT, restTemplate), null, optionalLeaveHistory, "cancelled")).send();
		}
		if (cancelDecision.equalsIgnoreCase("Withdraw")) {
			Optional<ActiveLeaves> optionalLeave = activeLeaveRepo.findById(leaveRequestId);
			if (!optionalLeave.isPresent()) {
				logger.error("No data found in Active Leave table for Leave Request Id {}", leaveRequestId);
				throw new ResourceNotFoundException(
						"No data found in Active Leave table for Leave Request Id " + leaveRequestId);
			}

			LeaveHistory leaveHistory = LeaveHelper.copyActiveToHistory(optionalLeave.get());
			activeLeaveRepo.deleteById(leaveRequestId);
			leaveHistory.setLeaveStatus("CANCELLED");
			leaveHistoryRepo.save(leaveHistory);
			incrementLeaveCount(optionalLeave.get().getFromDate(), optionalLeave.get().getToDate(),
					optionalLeave.get().getEmployeeId(), optionalLeave.get().getLeaveId());
			if (checkForPendingLeaveApproval(optionalLeave.get().getFromDate(), optionalLeave.get().getToDate(),
					optionalLeave.get().getDepartmentId())) {
				logger.info("Approved one pending leave");
			}
			int id = optionalLeave.get().getEmployeeId();
			String subject = "Leave Application Cancelled for User Id : " + id;
			(new NotifyLeaveHistory(subject, new Mailer(id, userService, NOTIFY_EMAIL_ENDPOINT, restTemplate), optionalLeave, null, "withdrawn")).send();

		}
		return true;
	}

	private boolean checkForPendingLeaveApproval(Date fromDate, Date toDate, int departmentId) {
		List<ActiveLeaves> pendingLeaves = activeLeaveRepo.findByFromDateAndToDateAndDepartmentId(fromDate, toDate,
				departmentId);
		if (pendingLeaves.isEmpty()) {
			logger.info("No pending leaves to approve");
			return false;
		}
		ActiveLeaves selectedLeave = pendingLeaves.get(0);
		LeaveHistory leaveHistory = LeaveHelper.copyActiveToHistory(selectedLeave);
		leaveHistory.setLeaveStatus("APPROVED");
		leaveHistoryRepo.save(leaveHistory);
		activeLeaveRepo.deleteById(selectedLeave.getLeaveRequestId());
		logger.info("Approved leave request with id {}", selectedLeave.getLeaveRequestId());
		int id = selectedLeave.getEmployeeId();
		String subject = "Leave Application Approved for User Id : " + id;
		(new NotifyLeaveHistory(subject, new Mailer(id, userService, NOTIFY_EMAIL_ENDPOINT, restTemplate), Optional.of(selectedLeave), null, "approved")).send();
		return true;
	}

	public boolean incrementLeaveCount(Date fromDate, Date toDate, int employeeId, int leaveId) {
		long diffInMillies = Math.abs(toDate.getTime() - fromDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
		LeaveStatsId statsId = new LeaveStatsId();
		statsId.setEmployeeId(employeeId);
		statsId.setLeaveId(leaveId);
		Optional<LeaveStats> leaveStats = leaveStatsRepo.findById(statsId);
		if (!leaveStats.isPresent()) {
			logger.error("No datafound in stats table for userId {}", employeeId);
			throw new ResourceNotFoundException("No data found in stats table for user " + employeeId);
		}
		leaveStats.get().setLeaveCount(leaveStats.get().getLeaveCount() + diff);
		leaveStatsRepo.save(leaveStats.get());
		return true;
	}

	public Boolean undoLeaveDecision(Integer leaveRequestId) throws Exception {
		LeaveMementoCareTaker leaveMementoCareTaker = new LeaveMementoCareTaker();

		LeaveMemento leaveSnapShot = leaveMementoCareTaker.restoreMemento(leaveRequestId);
		if(leaveSnapShot.getLeaveStatus().equalsIgnoreCase("APPROVED")) {
			//Undo Approval
			return cancelWithdrawLeave(leaveRequestId, "Cancel");
		}
		else {
			Optional<LeaveHistory> optionalLeaveHistory = leaveHistoryRepo
					.findByLeaveHistoryIdLeaveRequestId(leaveRequestId);
			if (!optionalLeaveHistory.isPresent()) {
				logger.error("No data found in Leave History table for Leave Request Id {}", leaveRequestId);
				throw new ResourceNotFoundException(
						"No data found in Leave History table for Leave Request Id " + leaveRequestId);
			}		
			
			optionalLeaveHistory.get().setLeaveStatus("APPROVED");
			leaveHistoryRepo.save(optionalLeaveHistory.get());
		}
		return true;
	}
	
	@Scheduled(cron = "0 */2 * ? * *")
	public void print() {
		System.out.println(" Cron called");
	}

	public String getSummary(Integer employeeId, String type) {
		logger.info("Received request for sending summary of employee {}", employeeId);		
		return target.getSummary(employeeId, type);
	}

	@Override
	public List<Manager> getManagers(String departmentName) {
		ManagerList managerList = new ManagerListProxyImpl();
		Department department = new Department("Computer", 10, 5, managerList);
		managerList = department.getManagerList();
		return managerList.getManagerList(departmentName);
	}
	
	@HystrixCommand(fallbackMethod = "getDefaultUsers")
    public List<Integer> getUsersForRuleCondition(String expression) {
		return restTemplate.postForObject(
				new StringBuilder(userService).append(USER_DETAILS_ENDPOINT).toString(),
				expression, List.class);
    }
 
    private List<Integer> defaultGreeting() {
    	return restTemplate.getForObject(
				new StringBuilder(userService).append(USER_DETAILS_ENDPOINT).toString(),
				 List.class);    }
}