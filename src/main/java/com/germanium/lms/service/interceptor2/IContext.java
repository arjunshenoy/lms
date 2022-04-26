package com.germanium.lms.service.interceptor2;

import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.service.ILeaveService;

public interface IContext {
	public int fetchDetails();
	public void turnOffAutoApproval();
	public void turnOnAutoApproval();
	public void updateContext();
	public void updateTheLimitHours(double scaleIt);
}
