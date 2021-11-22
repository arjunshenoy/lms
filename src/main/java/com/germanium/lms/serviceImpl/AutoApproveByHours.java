package com.germanium.lms.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.repository.ILeaveHistoryRepository;
import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.service.decorator.AutoApproveDecorator;
import com.germanium.lms.service.decorator.IAutoApprove;

public class AutoApproveByHours extends AutoApproveDecorator{
	private ILeaveHistoryRepository leaveHistRepo;
	@Value("${user.service.url}")
	private String userService;

	public AutoApproveByHours(IAutoApprove decoratedRule, ILeaveHistoryRepository leaveHistRepo) {
		super(decoratedRule);
		this.leaveHistRepo = leaveHistRepo;
	}
	@Override
	public String checkApprovalRule(Leave leaveRequest, String prev) {
		int depId = leaveRequest.getDepartmentId();
		Date current = leaveRequest.getFromDate();
		Date end = leaveRequest.getToDate();

		String result = prev;
		while (!result.equals("reject") && current.before(end)) {
			List<LeaveHistory> leaves = leaveHistRepo.findClashingLeaves(current);
			if (leaves.size()*8 > getWorkingHoursRequired(depId)) {
				result = "reject";
				break; 
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(current);
			calendar.add(Calendar.DATE, 1);
			current = calendar.getTime();
		}
		
		return getRejectOrQueue(leaveRequest, result);
	}
	
	public float getWorkingHoursRequired(int depId) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl
		  = userService + "/api/v1/department/" +String.valueOf(depId) + "/workHours";
		ResponseEntity<Float> response
		  = restTemplate.getForEntity(resourceUrl, Float.class);
		return response.getBody();
	}
	
}