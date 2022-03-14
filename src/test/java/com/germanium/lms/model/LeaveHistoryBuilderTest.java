package com.germanium.lms.model;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LeaveHistoryBuilderTest {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

	@Test
	public void testLeaveHistory_EmptyBuilderConstructor() {
		LeaveHistory history = LeaveHistory.builder().build();
		assertNotNull(history);
	}
	
	@Test
	public void  testLeaveHistory_BuilderConstructor() throws ParseException {
		LeaveHistoryId leaveHistoryId = new LeaveHistoryId();
		leaveHistoryId.setLeaveRequestId(10);
		leaveHistoryId.setEmployeeId(1);
		leaveHistoryId.setDateOfApplication((format.parse("2021/05/10")));		
		
		LeaveHistory history = LeaveHistory.builder()
				.decisionDate(format.parse("2021/05/10"))
				.fromDate(format.parse("2021/05/11"))
				.toDate(format.parse("2021/05/11"))
				.departmentId(1)
				.leaveId(1)
				.leaveHistoryId(leaveHistoryId)
				.comments("Comment")
				.leaveStatus("Status")
				.reason("Reason")
				.build();
		
		assertNotNull(history);
		assertEquals(history.getComments(), "Comment");
		assertEquals(history.getDecisionDate(),format.parse("2021/05/10") );
		assertEquals(history.getFromDate(),format.parse("2021/05/11") );
		assertEquals(history.getToDate(),format.parse("2021/05/11") );
		assertEquals(history.getDepartmentId(), 1);
		assertEquals(history.getLeaveId(), 1);
		assertEquals(history.getLeaveHistoryId(), leaveHistoryId);
		assertEquals(history.getLeaveStatus(), "Status");
		assertEquals(history.getReason(), "Reason");

		
				
	}
}
