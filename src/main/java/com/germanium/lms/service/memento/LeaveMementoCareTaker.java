package com.germanium.lms.service.memento;

import java.util.HashMap;
import java.util.Map;

import com.germanium.lms.model.ActiveLeaves;

public class LeaveMementoCareTaker implements MementoCareTaker {

	protected static Map<Integer, LeaveMemento> mementoHistory = new HashMap<Integer, LeaveMemento>();

	public LeaveMemento createNewActiveLeaveMemento(ActiveLeaves activeLeave) {

		return activeLeave.createMemento();
	}

	public  void addMementoToCache(ActiveLeaves activeLeave, String decision) {
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
