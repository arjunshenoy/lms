package com.germanium.lms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.LeaveStatsId;
import com.germanium.lms.repository.ILeaveRulesRepository;
import com.germanium.lms.repository.ILeaveStatisticsRepository;
import com.germanium.lms.serviceImpl.LeaveServiceImpl;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LeaveServiceImplTest {

	@Autowired
	private LeaveServiceImpl leaveServiceImpl;

	@Autowired
	private ILeaveRulesRepository leaveRulesRepository;
	
	@Autowired
	private ILeaveStatisticsRepository leaveStatsRepo;

	@Test
	public void createLeaveRulesTest() { 
		List<LeaveRules> expectedList = createLeaveRulesList();

		List<LeaveRules> actualLeaveRules = leaveServiceImpl.createLeaveRules(expectedList);

		assertEquals(expectedList.size(), actualLeaveRules.size());
		assertNotNull(actualLeaveRules);
		assertEquals(expectedList.get(0).getLeaveId(), actualLeaveRules.get(0).getLeaveId());
		assertEquals(expectedList.get(0).getName(), actualLeaveRules.get(0).getName());
	}

	private List<LeaveRules> createLeaveRulesList() { 
		List<LeaveRules> leaveRulesList = new ArrayList<>();

		LeaveRules leaveRule = new LeaveRules();
		leaveRule.setLeaveId(1);
		leaveRule.setName("casual leave");
		leaveRule.setCombinableLeaves("hello");

		
		leaveRulesList.add(leaveRule);
		return leaveRulesList; 
	}

	@Test
	public void findLeavesByIdExistTest() throws Exception {
		List<LeaveRules> leaveRuleList = createLeaveRulesList();
		leaveRulesRepository.saveAll(leaveRuleList);
		LeaveRules actualLeaveRule = leaveServiceImpl.findLeavesById(1);
		assertNotNull(actualLeaveRule); 
		assertEquals(actualLeaveRule.getLeaveId(),1);
		assertEquals(actualLeaveRule.getName(),"casual leave");

	}
	@Test 
	public void findLeavesByIdNotExistTest() throws Exception {
		try {
			LeaveRules actualLeaveRule = leaveServiceImpl.findLeavesById(3);

			assertNotNull(actualLeaveRule); 
			assertEquals(actualLeaveRule.getLeaveId(),3);
			assertEquals(actualLeaveRule.getName(),"casual leave");
		} catch (Exception e) {
			Integer leaveId = 3;
			assertEquals("Leave With Leave Id: Not Found " + leaveId , e.getMessage());
		} 		
	}

	@Test
	public void updateLeaveRulesTest() throws Exception{ 
		
			List<LeaveRules> leaveRuleList = createLeaveRulesList();
			leaveRulesRepository.saveAll(leaveRuleList);
			leaveRuleList.get(0).setName("sick leave");

			LeaveRules updatedRule = leaveServiceImpl.updateLeaveRules(1,leaveRuleList.get(0));

			assertEquals("sick leave", updatedRule.getName());
			List<LeaveRules> leave = (List<LeaveRules>) leaveRulesRepository.findAll();

			assertEquals("sick leave", leave.get(0).getName());
			assertEquals(1, leave.size());
		
	}

	@Test
	public void deleteLeaveRulesTest() throws Exception{
		LeaveRules leaveRule = new LeaveRules();
		leaveRule.setCarryOverCount(1);
		leaveRule.setCombinableLeaves("Maternity");
		leaveRule.setMaxLeavesCount(5);
		leaveRule.setCostIncurred(100);
		leaveRule.setName("New Leave");
		leaveRulesRepository.save(leaveRule);		
		leaveServiceImpl.deleteLeaveRules(2);
		assertThat(leaveRulesRepository.existsById(2)).isFalse(); 	
	}
	
	@Test
	public void findLeaveRulesTest() {
		List<LeaveRules> leaveRulesList = leaveServiceImpl.getLeaveRules();
		assertNotNull(leaveRulesList);
	}
	
	@Test
	public void updateLeaveRulesTestForIdNotExistTest() {
		try {
			leaveServiceImpl.updateLeaveRules(10, new LeaveRules());
		}catch (Exception e) {
			assertEquals(e.getMessage(), "Leave With Leave Id: Not Found 10");
		}
	}
	
	@Test
	public void deleteLeaveRulesTestForIdNotExistTest() {
		try {
			leaveServiceImpl.deleteLeaveRules(10);
		}catch (Exception e) {
			assertEquals(e.getMessage(), "Leave With Leave Id: Not Found 10");
		}
	}
	
	@Test
	public void getLeaveStatsByIdTest() {
		LeaveStats leaveStat = new LeaveStats();
		LeaveStatsId leaveStatsId = new LeaveStatsId();
		leaveStatsId.setEmployeeId(1);
		leaveStatsId.setLeaveId(1);
		leaveStat.setId(leaveStatsId);
		leaveStat.setLeaveCount(10);
		leaveStatsRepo.save(leaveStat);
		
		List<LeaveStats> leaveStats = leaveServiceImpl.getLeaveStatsById(1);
		assertNotNull(leaveStats);
		assertEquals(1, leaveStats.size());
	}
	
	@Test
	public void addLeaveStatsForNewUsersTest() {
		leaveServiceImpl.addLeaveStatsForNewUsers(2);
		List<LeaveStats> leaveStats = leaveStatsRepo.findAll();
		assertNotNull(leaveStats);
	}
}