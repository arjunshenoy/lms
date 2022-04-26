package com.germanium.lms.service.memento;

import java.util.HashMap;
import java.util.Map;

import com.germanium.lms.model.ActiveLeaves;

public class LeaveMementoCareTaker implements MementoCareTaker {

	protected static Map<Integer, LeaveMemento> mementoHistory = new HashMap<Integer, LeaveMemento>();

	public LeaveMemento createNewActiveLeaveMemento(ActiveLeaves activeLeave) {

		return CreateMemento(activeLeave);
	}

	public static LeaveMemento CreateMemento(ActiveLeaves activeLeave) {

		return new LeaveMemento(activeLeave.getLeaveRequestId(), activeLeave.getEmployeeId(),
				activeLeave.getDateOfApplication(), activeLeave.getLeaveId(), activeLeave.getDepartmentId(),
				activeLeave.getFromDate(), activeLeave.getToDate(), activeLeave.getReason(), activeLeave.getComments(),
				activeLeave.getLeaveName(), activeLeave.getLeaveStatus());
	}

	public ActiveLeaves restore(LeaveMemento memento) {
		ActiveLeaves active = new ActiveLeaves();
		if (memento != null) {
			active.setLeaveRequestId(memento.getLeaveRequestId());
			active.setEmployeeId(memento.getEmployeeId());
			active.setDateOfApplication(memento.getDateOfApplication());
			active.setLeaveId(memento.getLeaveId());
			active.setDepartmentId(memento.getDepartmentId());
			active.setFromDate(memento.getFromDate());
			active.setToDate(memento.getToDate());
			active.setReason(memento.getReason());
			active.setComments(memento.getComments());
			active.setLeaveName(memento.getLeaveName());
			active.setLeaveStatus(memento.getLeaveStatus());
		} else {
			// throw new LeaveServiceException("Cannot Perform restore with null memento
			// object");

		}
		return active;
	}

	public void addMementoToCache(ActiveLeaves activeLeave, String decision) {
		if (decision != null && decision.trim().length() != 0) {
			LeaveMemento mementoMessageMap = mementoHistory.get(activeLeave.getLeaveRequestId());
			if (mementoMessageMap == null) {
				mementoHistory.put(activeLeave.getLeaveRequestId(), createNewActiveLeaveMemento(activeLeave));
			}
		}

	}

	public LeaveMemento restoreMemento(int leaveId) throws Exception {

		LeaveMemento leaveMemento = null;
		leaveMemento = mementoHistory.get(leaveId);
		if (leaveMemento == null) {
			throw new Exception("Unable to find Memento");
		}
		return leaveMemento;
	}

}
