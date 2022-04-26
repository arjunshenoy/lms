package com.germanium.lms.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.repository.ILeaveHistoryRepository;
import com.germanium.lms.service.ILeaveUtilService;
import com.germanium.lms.service.adapter.ITarget;
import com.germanium.lms.service.decorator.IAutoApprove;

public class LeaveUtilServiceImpl implements ILeaveUtilService {

	Logger logger = LoggerFactory.getLogger(LeaveUtilServiceImpl.class);

	IAutoApprove autoApproval =  new AutoApproveCache();  

	@Autowired
	ILeaveHistoryRepository leaveHistoryRepo;
	
	@Autowired
	ITarget target;
	
	@Override
	public void enableAutoApproval() {				
		// decorate/chain with each rule
		 autoApproval = new AutoApproveByEmployeeNumber(
				new AutoApproveByHours(new AutoApproveQueue(), leaveHistoryRepo), leaveHistoryRepo); 		
		
	}
	
	@Override
	public void disableAutoApproval() {				
			autoApproval = new AutoApproveCache();
			
	}
	
	@Override
	public String autoApproval(Leave leaveRequest) {
		return autoApproval.checkApprovalRule(leaveRequest, "approve");
	}
	
	public String getSummary(Integer employeeId, String type) {
		logger.info("Received request for sending summary of employee {}", employeeId);		
		return target.getSummary(employeeId, type);
	}

}
