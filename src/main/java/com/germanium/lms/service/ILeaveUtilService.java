package com.germanium.lms.service;

import com.germanium.lms.model.factory.Leave;

public interface ILeaveUtilService {

	public void enableAutoApproval();

	public void disableAutoApproval();

	public String autoApproval(Leave leaveRequest);

	public String getSummary(Integer employeeId, String type);

}
