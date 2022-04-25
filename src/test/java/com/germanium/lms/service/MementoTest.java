package com.germanium.lms.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.isA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.LeaveStatsId;
import com.germanium.lms.model.dto.LeaveRequestDto;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.model.factory.LeaveFactory;
import com.germanium.lms.repository.IActiveLeaveRepository;
import com.germanium.lms.repository.ILeaveHistoryRepository;
import com.germanium.lms.repository.ILeaveStatisticsRepository;
import com.germanium.lms.service.memento.LeaveMemento;
import com.germanium.lms.service.memento.LeaveMementoCareTaker;
import com.germanium.lms.serviceImpl.LeaveRuleServiceImpl;
import com.germanium.lms.utils.LeaveHelper;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MementoTest {

	@Autowired
	private ILeaveService leaveService;

	@MockBean
	private IActiveLeaveRepository activeLeaveRepo;

	@MockBean
	private LeaveRuleServiceImpl leaveRuleService;

	@MockBean
	private ILeaveStatisticsRepository leaveStatsRepo;

	@MockBean
	private RestTemplate restTemplate;

	@MockBean
	private ILeaveHistoryRepository leaveHistoryRepo;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

	//@Test
	public void setDecisionCreatesLeaveMemento() throws Exception {

		LeaveHistory leaveHistory = getLeaveHistory();
		Optional<ActiveLeaves> optionalActiveLeave = Optional.of(getActiveLeave());
		when(leaveHistoryRepo.save(isA(LeaveHistory.class))).thenReturn(leaveHistory);
		LeaveMementoCareTaker caretaker  = new LeaveMementoCareTaker();
		LeaveMemento leaveMemento = caretaker.restoreMemento(1);
		//Boolean response = leaveService.se
		assertNotNull(leaveMemento);
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

	private LeaveHistory getLeaveHistory() throws ParseException {
		ActiveLeaves activeLeave = new ActiveLeaves();
		activeLeave.setDateOfApplication((format.parse("2021/05/10")));
		activeLeave.setFromDate((format.parse("2021/05/11")));
		activeLeave.setToDate((format.parse("2021/05/11")));
		activeLeave.setDepartmentId(1);
		activeLeave.setEmployeeId(1);
		activeLeave.setLeaveName("SICK LEAVE");
		activeLeave.setLeaveId(1);
		activeLeave.setLeaveRequestId(10);
		return LeaveHelper.copyActiveToHistory(activeLeave);
	}

}
