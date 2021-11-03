package com.germanium.lms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.germanium.lms.models.LeaveRules;
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
	public void createLeaveRulesTest() { 
		List<LeaveRules> expectedList = createLeaveRulesList();
		List<LeaveRules> actualLeaveRules = leaveServiceImpl.createLeaveRules(expectedList);
		assertEquals(expectedList.size(), actualLeaveRules.size());
		assertNotNull(actualLeaveRules);
		assertEquals(expectedList.get(0).getLeaveId(), actualLeaveRules.get(0).getLeaveId());
		assertEquals(expectedList.get(0).getName(), actualLeaveRules.get(0).getName());
		//assertEquals(1, leaveRulesRepository.findAll());
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
	public void findLeavesByIdTest() throws Exception {

		LeaveRules actualLeaveRule = leaveServiceImpl.findLeavesById(1);
		assertNotNull(actualLeaveRule); 
		assertEquals(actualLeaveRule.getLeaveId(),1);
		assertEquals(actualLeaveRule.getName(),"casual leave");

	}


	/*
	 * @Test (expected = Exception.class) public void findLeavesById_Exception(){
	 * Optional<LeaveRules> OptionalLeaveRule = Optional.empty();
	 * //when(userRepo.findById(isA(Integer.class))).thenReturn(optionalUser);
	 * leaveServiceImpl.findLeavesById(1); }
	 */


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

	/*
	 * @Test public void deleteLeaveRulesTest() throws Exception{
	 * //ArgumentCaptor<LeaveRules> leaveRule
	 * =ArgumentCaptor.forClass(LeaveRules.class);
	 * leaveServiceImpl.deleteLeaveRules(1);
	 * assertThat(leaveRulesRepository.existsById(1)).isFalse(); }
	 */

}
