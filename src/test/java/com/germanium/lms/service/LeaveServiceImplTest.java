package com.germanium.lms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.repository.ILeaveRulesRepository;
import com.germanium.lms.serviceImpl.LeaveServiceImpl;


@SpringBootTest

@RunWith(SpringJUnit4ClassRunner.class)
public class LeaveServiceImplTest {

	@Autowired
	private LeaveServiceImpl leaveServiceImpl;

	@Autowired
	private ILeaveRulesRepository leaveRulesRepository;

	@Test
	@Ignore
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
	@Ignore
	public void findLeavesByIdExistTest() throws Exception {
		List<LeaveRules> leaveRuleList = createLeaveRulesList();
		leaveRulesRepository.saveAll(leaveRuleList);
		LeaveRules actualLeaveRule = leaveServiceImpl.findLeavesById(1);
		assertNotNull(actualLeaveRule); 
		assertEquals(actualLeaveRule.getLeaveId(),1);
		assertEquals(actualLeaveRule.getName(),"casual leave");

	}
	@Test 
	@Ignore
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
	@Ignore
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
	@Ignore
	public void deleteLeaveRulesTest() throws Exception{
		
			leaveServiceImpl.deleteLeaveRules(1);
			assertThat(leaveRulesRepository.existsById(1)).isFalse(); 
				
	}
	
	

}
