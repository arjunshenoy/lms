package com.germanium.lms.service.interceptor2;

import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.service.ILeaveService;

public interface IContext {
	public void fetchContext(ILeaveService leavService, LeaveHistory leaveHistory);
	public void updateContext();
	
}
