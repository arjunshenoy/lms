package com.germanium.lms.service.interceptor2;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.repository.ILeaveHistoryRepository;
import com.germanium.lms.service.ILeaveService;
import com.germanium.lms.serviceImpl.LeaveServiceImpl;


public class Context implements IContext {
	
	@Autowired
	ILeaveHistoryRepository leaveHistRepo;
	
	@Value("${user.service.url}")
	private String userService = "http://localhost:8081";
	
	@Override
	public void fetchContext(ILeaveService leavService, LeaveHistory leaveHistory) {
		Date current = leaveHistory.getFromDate();
		Date end = leaveHistory.getToDate();
		float hrs = getParamRequired(userService, "/workHours", leaveHistory.getDepartmentId());
		//String result = prev;
		while (current.before(end)) {
			List<LeaveHistory> leaves = getClashLeaves(leaveHistRepo, current);
			if (leaves.size()*8 > hrs) {
				System.out.print("event triggered");
				break;
				
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(current);
			calendar.add(Calendar.DATE, 1);
			current = calendar.getTime();
		}
		
		return getRejectOrQueue(leaveRequest, result);
		
	}

	@Override
	public void updateContext() {
		// TODO Auto-generated method stub
		
	}
	protected List<LeaveHistory> getClashLeaves(ILeaveHistoryRepository leaveHistRepo, Date current) {
		return leaveHistRepo.findClashingLeaves(current);
	}
	public float getParamRequired(String userService, String resource, int depId) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl
		  = userService + "/api/v1/department/" +depId + resource;
		ResponseEntity<Float> response
		  = restTemplate.getForEntity(resourceUrl, Float.class);
		return response.getBody();
	}

}
