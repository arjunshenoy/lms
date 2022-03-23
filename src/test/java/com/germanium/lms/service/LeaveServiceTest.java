package com.germanium.lms.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.LeaveHistoryId;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.LeaveStatsId;
import com.germanium.lms.model.dto.LeaveRequestDto;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.model.factory.LeaveFactory;
import com.germanium.lms.repository.IActiveLeaveRepository;
import com.germanium.lms.repository.ILeaveHistoryRepository;
import com.germanium.lms.repository.ILeaveStatisticsRepository;
import com.germanium.lms.serviceImpl.LeaveRuleServiceImpl;

import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LeaveServiceTest {

	@Autowired
	private ILeaveService leaveService;

	@MockBean
	private IActiveLeaveRepository activeLeaveRepo;

	@MockBean
	private LeaveRuleServiceImpl leaveRuleService;

	@MockBean
	private RestTemplate restTemplate;

	@MockBean
	private ILeaveStatisticsRepository leaveStatsRepo;

	@MockBean
	private ILeaveHistoryRepository leaveHistoryRepo;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

	@Test
	public void getActiveLeavesByIdTest() throws ParseException {
		ActiveLeaves activeLeave = new ActiveLeaves();
		activeLeave.setDateOfApplication(format.parse("2021/05/10"));
		activeLeave.setLeaveId(1);
		activeLeave.setDepartmentId(1);
		activeLeave.setEmployeeId(1);
		activeLeave.setLeaveName("Sick");
		activeLeave.setFromDate((format.parse("2021/05/11")));
		activeLeave.setToDate((format.parse("2021/05/12")));
		activeLeave.setLeaveRequestId(1);
		Optional<ActiveLeaves> optionalLeave = Optional.of(activeLeave);
		when(activeLeaveRepo.findById(anyInt())).thenReturn(optionalLeave);
		ActiveLeaves leave = leaveService.getActiveLeavesById(1);
		assertNotNull(leave);
		assertEquals(1, leave.getDepartmentId());
		assertEquals(1, leave.getEmployeeId());
		assertEquals(1, leave.getLeaveId());
		assertEquals("Sick", leave.getLeaveName());
	}

	@Test
	public void getActiveLeavesByIdWithNoValueInDBTest() throws ParseException {
		Optional<ActiveLeaves> optionalLeave = Optional.empty();
		when(activeLeaveRepo.findById(anyInt())).thenReturn(optionalLeave);
		try {
			leaveService.getActiveLeavesById(1);
		} catch (Exception e) {
			assertEquals("Leave Request with id: not found 1", e.getMessage());
		}
	}

	@Test
	public void createLeaveRequestTest() throws Exception {
		Leave leaveRequest = createLeaveRequest();
		when(leaveRuleService.checkLeaveTypeRequestedForUserId(anyInt(), anyInt())).thenReturn(true);
		LeaveStatsId id = new LeaveStatsId();
		id.setEmployeeId(1);
		id.setLeaveId(1);
		LeaveStats stats = new LeaveStats();
		stats.setId(id);
		stats.setLeaveCount(10);
		Optional<LeaveStats> optionalStats = Optional.of(stats);
		when(leaveStatsRepo.findById(any())).thenReturn(optionalStats);
		when(activeLeaveRepo.save(any())).thenReturn(getActiveLeave());
		ActiveLeaves activeLeave = leaveService.createLeaveRequest(leaveRequest);
		assertNotNull(activeLeave);
	}

	@Test
	public void createLeaveRequestWithNoLeaveTypeTest() throws Exception {
		Leave leaveRequest = createLeaveRequest();
		when(leaveRuleService.checkLeaveTypeRequestedForUserId(anyInt(), anyInt())).thenReturn(true);
		Optional<LeaveStats> optionalStats = Optional.empty();
		when(leaveStatsRepo.findById(any())).thenReturn(optionalStats);
		try {
			leaveService.createLeaveRequest(leaveRequest);
		} catch (Exception e) {
			assertEquals("No data found in stats table for user 1", e.getMessage());
		}
	}

	@Test
	public void createLeaveRequestWithNoLeavStatsTest() throws Exception {
		Leave leaveRequest = createLeaveRequest();
		when(leaveRuleService.checkLeaveTypeRequestedForUserId(anyInt(), anyInt())).thenReturn(false);
		try {
			leaveService.createLeaveRequest(leaveRequest);
		} catch (Exception e) {
			assertEquals("User does not have the leave type requested", e.getMessage());
		}
	}

	@Test
	public void createLeaveRequestWithNoLeaveCountTest() throws Exception {
		Leave leaveRequest = createLeaveRequest();
		when(leaveRuleService.checkLeaveTypeRequestedForUserId(anyInt(), anyInt())).thenReturn(true);
		LeaveStatsId id = new LeaveStatsId();
		id.setEmployeeId(1);
		id.setLeaveId(1);
		LeaveStats stats = new LeaveStats();
		stats.setId(id);
		stats.setLeaveCount(1);
		Optional<LeaveStats> optionalStats = Optional.of(stats);
		when(leaveStatsRepo.findById(any())).thenReturn(optionalStats);
		try {
			leaveService.createLeaveRequest(leaveRequest);
		} catch (Exception e) {
			assertEquals("User does not have enough Leaves", e.getMessage());
		}
	}

	@Test
	public void takeLeaveDecisionApprovalTest() throws Exception {
		Optional<ActiveLeaves> optionalLeave = Optional.of(getActiveLeave());
		when(activeLeaveRepo.findById(any())).thenReturn(optionalLeave);
		LeaveHistory history = createLeaveHistory();
		history.setLeaveStatus("Approved");
		when(leaveHistoryRepo.save(any())).thenReturn(history);
		Boolean decision = leaveService.takeLeaveDecision(1, "approve");
		assertTrue(decision);
	}

	@Test
	public void takeLeaveDecisionRejectionTest() throws Exception {
		Optional<ActiveLeaves> optionalLeave = Optional.of(getActiveLeave());
		when(activeLeaveRepo.findById(any())).thenReturn(optionalLeave);
		LeaveStatsId id = new LeaveStatsId();
		id.setEmployeeId(1);
		id.setLeaveId(1);
		LeaveStats stats = new LeaveStats();
		stats.setId(id);
		stats.setLeaveCount(1);
		Optional<LeaveStats> optionalStats = Optional.of(stats);
		when(leaveStatsRepo.findById(any())).thenReturn(optionalStats);
		LeaveHistory history = createLeaveHistory();
		history.setLeaveStatus("REJECTED");
		when(leaveHistoryRepo.save(any())).thenReturn(history);
		Boolean decision = leaveService.takeLeaveDecision(1, "rejected");
		assertTrue(decision);

	}

	@Test
	public void takeLeaveDecisionRejectionWIthNoLeaveStatTest() throws Exception {
		Optional<ActiveLeaves> optionalLeave = Optional.of(getActiveLeave());
		when(activeLeaveRepo.findById(any())).thenReturn(optionalLeave);
		LeaveHistory history = createLeaveHistory();
		history.setLeaveStatus("REJECTED");
		when(leaveHistoryRepo.save(any())).thenReturn(history);
		try {
			leaveService.takeLeaveDecision(1, "rejected");
		} catch (Exception e) {
			assertEquals("No data found in stats table for user 1", e.getMessage());
		}
	}

	@Test
	public void takeLeaveDecisionRejectionWIthNoLeaveStatTest2() throws Exception {
		Optional<ActiveLeaves> optionalLeave = Optional.empty();
		when(activeLeaveRepo.findById(any())).thenReturn(optionalLeave);
		LeaveHistory history = createLeaveHistory();
		history.setLeaveStatus("REJECTED");
		when(leaveHistoryRepo.save(any())).thenReturn(history);
		try {
			leaveService.takeLeaveDecision(1, "rejected");
		} catch (Exception e) {
			assertEquals("Leave Request with id: not found 1", e.getMessage());
		}
	}
	
	@Test
	public void takeLeaveDecisionRejectionWIthNoLeaveStatTest3() throws Exception {
		Optional<ActiveLeaves> optionalLeave = Optional.of(getActiveLeave());
		when(activeLeaveRepo.findById(any())).thenReturn(optionalLeave);
		LeaveHistory history = createLeaveHistory();
		history.setLeaveStatus("REJECTED");
		when(leaveHistoryRepo.save(any())).thenReturn(history);
		try {
			leaveService.takeLeaveDecision(1, "rejected");
		} catch (Exception e) {
			assertEquals("No data found in stats table for user 1", e.getMessage());
		}
	}
	
	@Test
	public void takeLeaveDecisionRejectionWIthNoLeaveStatTest4() throws Exception {
		Optional<ActiveLeaves> optionalLeave = Optional.of(getActiveLeave());
		when(activeLeaveRepo.findById(any())).thenReturn(optionalLeave);
		LeaveHistoryId leaveHistoryId = new LeaveHistoryId();
		leaveHistoryId.setLeaveRequestId(11);
		leaveHistoryId.setEmployeeId(1);
		leaveHistoryId.setDateOfApplication((format.parse("2021/05/10")));
		LeaveHistory history = createLeaveHistory();
		history.setLeaveStatus("REJECTED");
		history.setLeaveHistoryId(leaveHistoryId);
		when(leaveHistoryRepo.save(any())).thenReturn(history);
		try {
			leaveService.takeLeaveDecision(1, "rejected");
		} catch (Exception e) {
			assertEquals("Failed to add leave to history table 1", e.getMessage());
		}
	}

	@Test
	public void cancelWithdrawLeaveTest() throws ParseException {
		Optional<LeaveHistory> optionalHistory = Optional.of(createLeaveHistory());
		when(leaveHistoryRepo
					.findByLeaveHistoryIdLeaveRequestId(anyInt())).thenReturn(optionalHistory);
		when(activeLeaveRepo.findById(anyInt())).thenReturn(Optional.empty());
		LeaveStatsId id = new LeaveStatsId();
		id.setEmployeeId(1);
		id.setLeaveId(1);
		LeaveStats stats = new LeaveStats();
		stats.setId(id);
		stats.setLeaveCount(10);
		Optional<LeaveStats> optionalStats = Optional.of(stats);
		when(leaveStatsRepo.findById(any())).thenReturn(optionalStats);
		assertTrue(leaveService.cancelWithdrawLeave(1, "cancel"));
	}
	
	@Test
	public void cancelWithdrawLeaveCancelWithNoLeaveTest() throws ParseException {
		when(leaveHistoryRepo
					.findByLeaveHistoryIdLeaveRequestId(anyInt())).thenReturn(Optional.empty());
		try{
			leaveService.cancelWithdrawLeave(1, "cancel");
		}catch (Exception e) {
			assertEquals("No data found in Leave History table for Leave Request Id 1", e.getMessage());
		}
	}
	
	@Test
	public void cancelWithdrawLeaveWithdrawTest() throws ParseException {
		Optional<LeaveHistory> optionalHistory = Optional.of(createLeaveHistory());
		when(leaveHistoryRepo
					.findByLeaveHistoryIdLeaveRequestId(anyInt())).thenReturn(optionalHistory);
		LeaveStatsId id = new LeaveStatsId();
		id.setEmployeeId(1);
		id.setLeaveId(1);
		LeaveStats stats = new LeaveStats();
		stats.setId(id);
		stats.setLeaveCount(10);
		Optional<LeaveStats> optionalStats = Optional.of(stats);
		when(leaveStatsRepo.findById(any())).thenReturn(optionalStats);
		Optional<ActiveLeaves> optionalLeave = Optional.of(getActiveLeave());
		when(activeLeaveRepo.findById(any())).thenReturn(optionalLeave);
		assertTrue(leaveService.cancelWithdrawLeave(1, "withdraw"));
	}
	
	@Test
	public void cancelWithdrawLeaveWithdrawNoLeaveTest() throws ParseException {
		when(activeLeaveRepo.findById(anyInt())).thenReturn(Optional.empty());
		try{
			leaveService.cancelWithdrawLeave(1, "withdraw");
		}catch (Exception e) {
			assertEquals("No data found in Active Leave table for Leave Request Id 1", e.getMessage());
		}
	}
	
	@Test
	public void cancelWithdrawLeaveWithdrawAndApprovalTest() throws ParseException {
		Optional<LeaveHistory> optionalHistory = Optional.of(createLeaveHistory());
		when(leaveHistoryRepo
					.findByLeaveHistoryIdLeaveRequestId(anyInt())).thenReturn(optionalHistory);
		LeaveStatsId id = new LeaveStatsId();
		id.setEmployeeId(1);
		id.setLeaveId(1);
		LeaveStats stats = new LeaveStats();
		stats.setId(id);
		stats.setLeaveCount(10);
		Optional<LeaveStats> optionalStats = Optional.of(stats);
		when(leaveStatsRepo.findById(any())).thenReturn(optionalStats);
		Optional<ActiveLeaves> optionalLeave = Optional.of(getActiveLeave());
		when(activeLeaveRepo.findById(any())).thenReturn(optionalLeave);
		List<ActiveLeaves> activeLeaveList = new ArrayList<>();
		ActiveLeaves leave1 = getActiveLeave();
		leave1.setLeaveRequestId(11);
		activeLeaveList.add(leave1);
		when(activeLeaveRepo.findByFromDateAndToDateAndDepartmentId(any(), any(), anyInt())).thenReturn(activeLeaveList);
		assertTrue(leaveService.cancelWithdrawLeave(1, "withdraw"));
	}
	
	@Test
	public void cancelWithdrawLeaveWithApprovalTest() throws ParseException {
		Optional<LeaveHistory> optionalHistory = Optional.of(createLeaveHistory());
		when(leaveHistoryRepo
					.findByLeaveHistoryIdLeaveRequestId(anyInt())).thenReturn(optionalHistory);
		when(activeLeaveRepo.findById(anyInt())).thenReturn(Optional.empty());
		LeaveStatsId id = new LeaveStatsId();
		id.setEmployeeId(1);
		id.setLeaveId(1);
		LeaveStats stats = new LeaveStats();
		stats.setId(id);
		stats.setLeaveCount(10);
		Optional<LeaveStats> optionalStats = Optional.of(stats);
		when(leaveStatsRepo.findById(any())).thenReturn(optionalStats);
		List<ActiveLeaves> activeLeaveList = new ArrayList<>();
		ActiveLeaves leave1 = getActiveLeave();
		leave1.setLeaveRequestId(11);
		activeLeaveList.add(leave1);
		when(activeLeaveRepo.findByFromDateAndToDateAndDepartmentId(any(), any(), anyInt())).thenReturn(activeLeaveList);
		assertTrue(leaveService.cancelWithdrawLeave(1, "cancel"));
	}
	
	private ActiveLeaves getActiveLeave() throws ParseException {
		ActiveLeaves activeLeave = new ActiveLeaves();
		activeLeave.setDateOfApplication((format.parse("2021/05/10")));
		activeLeave.setFromDate((format.parse("2021/05/11")));
		activeLeave.setToDate((format.parse("2021/05/11")));
		activeLeave.setDepartmentId(1);
		activeLeave.setEmployeeId(1);
		activeLeave.setLeaveName("SICK LEAVE");
		activeLeave.setLeaveId(1);
		activeLeave.setLeaveRequestId(10);
		return activeLeave;
	}

	private Leave createLeaveRequest() throws ParseException {
		LeaveRequestDto request = new LeaveRequestDto();
		request.setDepartmentId(1);
		request.setEmployeeId(1);
		request.setFromDate((format.parse("2021/05/11")));
		request.setToDate((format.parse("2021/05/13")));
		request.setDateOfApplication((format.parse("2021/05/10")));
		request.setLeaveName("SICK LEAVE");
		request.setLeaveId(1);
		Leave leaveObject = LeaveFactory.getNewLeaveObject(request);
		return leaveObject;
	}

	private LeaveHistory createLeaveHistory() throws ParseException {
		LeaveHistoryId leaveHistoryId = new LeaveHistoryId();
		leaveHistoryId.setLeaveRequestId(10);
		leaveHistoryId.setEmployeeId(1);
		leaveHistoryId.setDateOfApplication((format.parse("2021/05/10")));
		/*
		 * LeaveHistory history = new LeaveHistory(null);
		 * history.setDecisionDate((format.parse("2021/05/10")));
		 * history.setFromDate((format.parse("2021/05/11")));
		 * history.setToDate((format.parse("2021/05/11"))); history.setDepartmentId(1);
		 * history.setLeaveId(1); history.setLeaveHistoryId(leaveHistoryId);
		 */
		
		LeaveHistory history = LeaveHistory.builder().
				decisionDate(format.parse("2021/05/10"))
				.fromDate(format.parse("2021/05/11"))
				.toDate(format.parse("2021/05/11"))
				.departmentId(1)
				.leaveId(1)
				.leaveHistoryId(leaveHistoryId)
				.build();
				
		return history;
	}
}
