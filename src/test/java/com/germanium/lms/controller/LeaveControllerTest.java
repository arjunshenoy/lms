package com.germanium.lms.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.germanium.lms.controllers.LeaveController;
import com.germanium.lms.models.LeaveRules;
import com.germanium.lms.service.ILeaveService;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = LeaveController.class )
public class LeaveControllerTest {

	@Autowired 
	private MockMvc mockMvc;

	@MockBean
	ILeaveService leaveService;

	@InjectMocks
	private LeaveController leaveController;

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void getLeaveRules_ReturnsStatusCodeOK() throws Exception {
		LeaveRules leaveRule1 = new LeaveRules();	
		leaveRule1.setLeaveId(1);
		leaveRule1.setName("Casual Leave");
		leaveRule1.setLeaveCount(20);
		leaveRule1.setCarryOverCount(5);

		LeaveRules leaveRule2 = new LeaveRules();	
		leaveRule2.setLeaveId(2);
		leaveRule2.setName("Casual Leave");
		leaveRule2.setLeaveCount(20);
		leaveRule2.setCarryOverCount(5);

		List<LeaveRules> leaveRulesList = List.of(leaveRule1,leaveRule2);
		when(leaveService.getLeaveRules()).thenReturn(leaveRulesList);

		mockMvc.perform(get("/api/v1/leave/leaveType")).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].leaveId").value("1"));

	}

	@Test
	public void createLeaveRules_ReturnStatusCodeOk() throws Exception {
		LeaveRules leaveRule1 = new LeaveRules();	
		leaveRule1.setLeaveId(1);
		leaveRule1.setName("Sick Leave");
		List<LeaveRules> leaveRulesList = List.of(leaveRule1);
		Mockito.when(leaveService.createLeaveRules(ArgumentMatchers.any())).thenReturn(leaveRulesList);
		String json = mapper.writeValueAsString(leaveRulesList);
		mockMvc.perform(post("/api/v1/leave/leaveType").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].leaveId").value("1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sick Leave"));
	}

	@Test
	public void updateLeaveRules_ReturnStatusCodeOk() throws Exception {
		LeaveRules leaveRule1 = new LeaveRules();	
		leaveRule1.setLeaveId(1);
		leaveRule1.setName("Sick Leave");	

		when(leaveService.updateLeaveRules(1, leaveRule1)).thenReturn(leaveRule1);
		String json = mapper.writeValueAsString(leaveRule1);

		MvcResult mvcResult = mockMvc.perform(put("/api/v1/leave/leaveType/{leaveId}",  1).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertEquals("Leave type Updated Successfully", mvcResult.getResponse().getContentAsString());

	}

	/*
	 * @Test public void deleteLeaveRules_ReturnStatusCodeOk() throws Exception {
	 * LeaveRules leaveRule1 = new LeaveRules(); leaveRule1.setLeaveId(1);
	 * leaveRule1.setName("Sick Leave"); Integer leaveId = 1;
	 * //when(leaveService.deleteLeaveRules(leaveId)).thenReturn(leaveRule1);
	 * //String json = mapper.writeValueAsString(leaveRule1);
	 * Mockito.doNothing().when(leaveService).deleteLeaveRules(leaveId);
	 * verify(leaveService).deleteLeaveRules(leaveId);
	 * 
	 * 
	 * }
	 */
}
