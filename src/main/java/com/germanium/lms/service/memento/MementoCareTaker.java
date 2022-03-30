package com.germanium.lms.service.memento;

import com.germanium.lms.model.ActiveLeaves;

public interface MementoCareTaker {

	public LeaveMemento createNewActiveLeaveMemento(ActiveLeaves activeLeave);

	public void addMementoToCache(ActiveLeaves activeLeave, String decision);

	public LeaveMemento restoreMemento(int leaveId) throws Exception;

}
