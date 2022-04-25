package com.germanium.lms.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.germanium.lms.controllers.LeaveController;
import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.model.LeaveStats;
import com.germanium.lms.model.LeaveStatsId;
import com.germanium.lms.service.ILeaveService;
import com.germanium.lms.serviceImpl.AutoApproveInvoker;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = LeaveController.class)
public class LeaveControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ILeaveService leaveService;
	
	@MockBean
	AutoApproveInvoker invoker;

	@InjectMocks
	private LeaveController leaveController;

	private static ObjectMapper mapper = new ObjectMapper();

	private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

	@Test
	public void getLeaveRules_ReturnsStatusCodeOK() {
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

		List<LeaveRules> leaveRulesList = List.of(leaveRule1, leaveRule2);
		when(leaveService.getLeaveRules()).thenReturn(leaveRulesList);

		try {
			mockMvc.perform(get("/api/v1/leave/leaveType")).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$[0].leaveId").value("1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void getLeavesByIdTest() {
		LeaveRules leaveRule1 = new LeaveRules();
		leaveRule1.setLeaveId(1);
		leaveRule1.setName("Casual Leave");
		leaveRule1.setLeaveCount(20);
		leaveRule1.setCarryOverCount(5);
		when(leaveService.findLeavesById(anyInt())).thenReturn(leaveRule1);

		try {
			mockMvc.perform(get("/api/v1/leave/leaveType/1")).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("leaveId").value("1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void getLeavesStatsByIdTest() {
		LeaveStatsId id = new LeaveStatsId();
		id.setEmployeeId(1);
		id.setLeaveId(1);
		LeaveStats stats = new LeaveStats();
		stats.setId(id);
		stats.setLeaveCount(10);
		List<LeaveStats> leaveStatsList = new ArrayList<>();
		leaveStatsList.add(stats);
		when(leaveService.getLeaveStatsById(anyInt())).thenReturn(leaveStatsList);

		try {
			mockMvc.perform(get("/api/v1/leave/leaveStats/1")).andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$[0].id.leaveId").value("1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void addLeaveStatsForNewUsersTest() {
		when(leaveService.addLeaveStatsForNewUsers(anyInt())).thenReturn(true);
		try {
			mockMvc.perform(post("/api/v1/leave/leaveStats/1")).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getActiveLeavesById() throws ParseException  {
		ActiveLeaves activeLeave = new ActiveLeaves();
		activeLeave.setDateOfApplication((format.parse("2021/05/10")));
		activeLeave.setFromDate((format.parse("2021/05/11")));
		activeLeave.setToDate((format.parse("2021/05/11")));
		activeLeave.setDepartmentId(1);
		activeLeave.setEmployeeId(1);
		activeLeave.setLeaveName("SICK LEAVE");
		activeLeave.setLeaveId(1);
		activeLeave.setLeaveRequestId(10);
		when(leaveService.getActiveLeavesById(anyInt())).thenReturn(activeLeave);
		try {
			mockMvc.perform(get("/api/v1/leave/request/1")).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void cancelWithdrawLeaveTest() {
		when(leaveService.addLeaveStatsForNewUsers(anyInt())).thenReturn(true);
		try {
			mockMvc.perform(post("/api/v1/leave/cancelRequest/1/cancel")).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void takeLeaveDecisionTest() throws Exception {	
		when(leaveService.takeLeaveDecision(anyInt(), any())).thenReturn(true);
		mockMvc.perform(post("/api/v1/leave/request/1/reject")).andExpect(status().isOk());
	}

	@Test
	public void createLeaveRules_ReturnStatusCodeOk() throws JsonProcessingException {
		LeaveRules leaveRule1 = new LeaveRules();
		leaveRule1.setLeaveId(1);
		leaveRule1.setName("Sick Leave");
		Mockito.when(leaveService.createLeaveRules(ArgumentMatchers.any())).thenReturn(leaveRule1);
		String json = mapper.writeValueAsString(leaveRule1);
		try {
			mockMvc.perform(post("/api/v1/leave/leaveType").contentType(MediaType.APPLICATION_JSON)
					.characterEncoding("utf-8").content(json).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("leaveId").value("1"))
					.andExpect(MockMvcResultMatchers.jsonPath("name").value("Sick Leave"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//@Test()
	public void updateLeaveRules_ReturnStatusCodeOk() throws UnsupportedEncodingException, JsonProcessingException {
		LeaveRules leaveRule1 = new LeaveRules();
		leaveRule1.setLeaveId(1);
		leaveRule1.setName("Sick Leave");

		when(leaveService.updateLeaveRules(1, leaveRule1)).thenReturn(leaveRule1);
		String json = mapper.writeValueAsString(leaveRule1);

		MvcResult mvcResult = null;
		try {
			mvcResult = mockMvc
					.perform(put("/api/v1/leave/leaveType/{leaveId}", 1).contentType(MediaType.APPLICATION_JSON)
							.characterEncoding("utf-8").content(json).accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Leave type Updated Successfully", mvcResult.getResponse().getContentAsString());

	}

	@Test
	public void deleteLeaveRules_ReturnStatusCodeOk() {
		LeaveRules leaveRule1 = new LeaveRules();
		leaveRule1.setLeaveId(1);
		leaveRule1.setName("Sick Leave");

		when(leaveService.deleteLeaveRules(anyInt())).thenReturn(true);
		try {
			mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/leave/leaveType/{leaveId}", 1)
					.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isOk())
					.andReturn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
