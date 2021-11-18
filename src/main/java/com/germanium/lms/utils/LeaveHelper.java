package com.germanium.lms.utils;

import java.util.Date;
import java.util.function.Function;

import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.LeaveHistoryId;
import com.germanium.lms.model.factory.Leave;

public class LeaveHelper {

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
		leaveHistory.setComments(activeLeave.getComments());
		return leaveHistory;

	}

	public static ActiveLeaves dtoToModelMapper(Leave leaveRequest) {
		ActiveLeaves activeLeave = new ActiveLeaves();

		Function<Leave, ActiveLeaves> converter = new Function<Leave, ActiveLeaves>() {

			public ActiveLeaves apply(Leave t) {
				activeLeave.setEmployeeId(t.getEmployeeId());
				activeLeave.setDateOfApplication(t.getDateOfApplication());
				activeLeave.setLeaveId(t.getLeaveId());
				activeLeave.setDepartmentId(t.getDepartmentId());
				activeLeave.setFromDate(t.getFromDate());
				activeLeave.setToDate(t.getToDate());
				activeLeave.setUpdatedTs(t.getUpdatedTs());
				activeLeave.setDocument(t.getDocument());
				activeLeave.setPay(t.getPay());
				activeLeave.setPayScale(t.getPayScale());
				activeLeave.setReason(t.getReason());
				return activeLeave;
			}
		};
		return converter.apply(leaveRequest);

	}

}
