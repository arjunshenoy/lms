package com.germanium.lms.utils;

import java.util.Date;

import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.LeaveHistoryId;

public class LeaveHistoryHelper {

	public static LeaveHistory copyActiveToHistory(ActiveLeaves activeLeave) {

		LeaveHistoryId leaveHistoryId = new LeaveHistoryId();
		leaveHistoryId.setLeaveRequestId(activeLeave.getLeaveRequestId());
		leaveHistoryId.setEmployeeId(activeLeave.getEmployeeId());
		leaveHistoryId.setDateOfApplication(activeLeave.getDateOfApplication());

		LeaveHistory leaveHistory = new LeaveHistory();
		leaveHistory.setLeaveHistoryId(leaveHistoryId);
		leaveHistory.setComments(activeLeave.getComments());
		leaveHistory.setDecisionDate(new Date());
		leaveHistory.setDepartmentId(activeLeave.getDepartmentId());
		leaveHistory.setFromDate(activeLeave.getFromDate());
		leaveHistory.setLeaveId(activeLeave.getLeaveId());

		leaveHistory.setReason(activeLeave.getReason());
		leaveHistory.setToDate(activeLeave.getToDate());
		return leaveHistory;

	}

}
