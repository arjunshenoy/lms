package com.germanium.lms.service.decorator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.repository.ILeaveHistoryRepository;

public abstract class AutoApproveDecorator implements IAutoApprove {
	protected IAutoApprove decoratedRule;
	
	protected  AutoApproveDecorator(IAutoApprove decoratedRule) {
		this.decoratedRule = decoratedRule;
	}

	public String getRejectOrQueue(Leave leaveRequest, String prev) {
		return decoratedRule.checkApprovalRule(leaveRequest, prev);
	}
	
	public String runHoursRule(Leave leaveRequest, ILeaveHistoryRepository leaveHistRepo, int scale, float hrs, String prev) {
		Date current = leaveRequest.getFromDate();
		Date end = leaveRequest.getToDate();
		String result = prev;
		while (!result.equals("reject") && current.before(end)) {
			List<LeaveHistory> leaves = getClashLeaves(leaveHistRepo, current);
			if (leaves.size()*scale > hrs) {
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
	
	public float getParamRequired(String userService, String resource, int depId) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl
		  = userService + "/api/v1/department/" +depId + resource;
		ResponseEntity<Float> response
		  = restTemplate.getForEntity(resourceUrl, Float.class);
		return response.getBody();
	}
	
	protected List<LeaveHistory> getClashLeaves(ILeaveHistoryRepository leaveHistRepo, Date current) {
		return leaveHistRepo.findClashingLeaves(current);
	}
}