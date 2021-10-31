package com.germanium.lms.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.germanium.lms.models.LeaveRules;
import com.germanium.lms.repository.ILeaveRulesRepository;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LeaveServiceImplTest {
	
	@Autowired
	private LeaveServiceImpl leaveServiceImpl;
	
	@Mock
	private ILeaveRulesRepository leaveRulesRepository;
	
	@Test
	public void createLeaveRulesTest() { 
	   List<LeaveRules> expectedList = createLeaveRulesList();
	   List<LeaveRules> actualLeaveRules = leaveServiceImpl.createLeaveRules(expectedList);
	   assertEquals(expectedList.size(), actualLeaveRules.size());
	   assertEquals(expectedList.get(0).getLeaveId(), actualLeaveRules.get(0).getLeaveId());
	   assertEquals(expectedList.get(0).getName(), actualLeaveRules.get(0).getName());
	   assertEquals(1, leaveRulesRepository.findAll());
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

}
