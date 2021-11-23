package com.germanium.lms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.LeaveStatsId;
import com.germanium.lms.repository.ILeaveRulesRepository;
import com.germanium.lms.repository.ILeaveStatisticsRepository;
import com.germanium.lms.serviceImpl.LeaveRuleServiceImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LeaveRuleServiceTest {

	@Autowired
	private LeaveRuleServiceImpl leaveRuleService;

	@MockBean
	private ILeaveRulesRepository leaveRuleRepo;

	@MockBean
	private ILeaveStatisticsRepository leaveStatRepo;

	@Test
	public void checkLeaveTypeRequestedForUserIdTets() {
		Optional<LeaveRules> leaveRules = Optional.of(createLeaveRule());
		when(leaveRuleRepo.findById(anyInt())).thenReturn(leaveRules);
		LeaveStats stats = createLeaveStats();
		when(leaveStatRepo.findLeaveTypeByUserIdAndLeaveId(anyInt(), anyInt())).thenReturn(stats);
		assertTrue(leaveRuleService.checkLeaveTypeRequestedForUserId(1, 1));
	}
	
	@Test
	public void checkLeaveTypeRequestedForUserIdTest() {
		Optional<LeaveRules> leaveRules = Optional.of(createLeaveRule());
		when(leaveRuleRepo.findById(anyInt())).thenReturn(leaveRules);
		when(leaveStatRepo.findLeaveTypeByUserIdAndLeaveId(anyInt(), anyInt())).thenReturn(null);
		assertFalse(leaveRuleService.checkLeaveTypeRequestedForUserId(1, 1));
	}

	@Test
	public void checkLeaveTypeRequestedForUserIdWithNoRulesTets() {
		Optional<LeaveRules> leaveRules = Optional.empty();
		when(leaveRuleRepo.findById(anyInt())).thenReturn(leaveRules);
		try {
			assertTrue(leaveRuleService.checkLeaveTypeRequestedForUserId(1, 1));
		} catch (Exception e) {
			assertEquals("Leave with leave Id : 1 not found", e.getMessage());
		}
	}
	
	@Test
	public void resetLeaveStatsTest() {
		List<LeaveRules> leaveRuleList = new ArrayList<>();
		leaveRuleList.add(createLeaveRule());
		when(leaveRuleRepo.findByLapseDate(anyString())).thenReturn(Optional.of(leaveRuleList));
		LeaveStats stats = createLeaveStats();
		List<LeaveStats> statsList = new ArrayList<>();
		statsList.add(stats);
		when(leaveStatRepo.findByIdLeaveId(anyInt())).thenReturn(statsList);
		assertTrue(leaveRuleService.resetLeaveStats());
	}

	@Test
	public void resetLeaveStatsTest2() {
		List<LeaveRules> leaveRuleList = new ArrayList<>();
		leaveRuleList.add(createLeaveRule());
		when(leaveRuleRepo.findByLapseDate(anyString())).thenReturn(Optional.of(leaveRuleList));
		LeaveStats stats = createLeaveStats();
		stats.setLeaveCount(5);
		List<LeaveStats> statsList = new ArrayList<>();
		statsList.add(stats);
		when(leaveStatRepo.findByIdLeaveId(anyInt())).thenReturn(statsList);
		assertTrue(leaveRuleService.resetLeaveStats());
	}
	
	private LeaveStats createLeaveStats() {
		LeaveStatsId id = new LeaveStatsId();
		id.setEmployeeId(1);
		id.setLeaveId(1);
		LeaveStats stats = new LeaveStats();
		stats.setId(id);
		stats.setLeaveCount(10);
		return stats;
	}

	private LeaveRules createLeaveRule() {
		LeaveRules leaveRule = new LeaveRules();
		leaveRule.setCarryOverCount(9);
		leaveRule.setCombinableLeaves("Maternity");
		leaveRule.setMaxLeavesCount(5);
		leaveRule.setCostIncurred(100);
		leaveRule.setName("New Leave");
		leaveRule.setLapseDate("2021/05/11");
		return leaveRule;
	}
}
