package com.germanium.lms.service.decorator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.factory.Leave;

public abstract class AutoApproveDecorator implements IAutoApprove {
	protected IAutoApprove decoratedRule;
	
	public AutoApproveDecorator(IAutoApprove decoratedRule) {
		this.decoratedRule = decoratedRule;
	}

	public String getRejectOrQueue(Leave leaveRequest, String prev) {
		String check = decoratedRule.checkApprovalRule(leaveRequest, prev);
		return check;
	}
	
	public String runHoursRule(Leave leaveRequest, int scale, float hrs, String prev) {
		Date current = leaveRequest.getFromDate();
		Date end = leaveRequest.getToDate();
		String result = prev;
		while (!result.equals("reject") && current.before(end)) {
			List<LeaveHistory> leaves = getClashLeaves(current);
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
	
	protected abstract List<LeaveHistory> getClashLeaves(Date current);
	
	public float getParamRequired(String userService, String resource, int depId) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl
		  = userService + "/api/v1/department/" +String.valueOf(depId) + resource;
		ResponseEntity<Float> response
		  = restTemplate.getForEntity(resourceUrl, Float.class);
		return response.getBody();
	}
}