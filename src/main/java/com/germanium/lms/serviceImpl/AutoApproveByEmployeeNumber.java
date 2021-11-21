package com.germanium.lms.serviceImpl;

import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.service.decorator.AutoApproveDecorator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.germanium.lms.repository.ILeaveHistoryRepository;

public class AutoApproveByEmployeeNumber extends AutoApproveDecorator {
	@Autowired
	ILeaveHistoryRepository leaveHistRepo;
	@Override
	public String checkApprovalRule(Leave leaveRequest) {
		Date current = leaveRequest.getFromDate();
		Date end = leaveRequest.getToDate();

		while (current.before(end)) {
			List<LeaveHistory> leaves = leaveHistRepo.findClashingLeaves(current);
			if (leaves.size()*8 > getWorkingHoursRequired()) {
				return "reject";
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(current);
			calendar.add(Calendar.DATE, 1);
			current = calendar.getTime();
		}
		
		return "approve";
	}
	
	public float getWorkingHoursRequired() {
		return 40;
	}
}
