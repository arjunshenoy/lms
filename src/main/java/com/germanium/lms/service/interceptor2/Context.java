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
import com.germanium.lms.service.ILeaveUtilService;
import com.germanium.lms.serviceImpl.LeaveServiceImpl;


public class Context implements IContext {

	private final ILeaveUtilService leaveService;
	private final LeaveHistory leaveHistory;
	private ILeaveHistoryRepository leaveHistRepo;
	private String userService;

	public Context(ILeaveUtilService leaveService,LeaveHistory leaveHistory,String userService, ILeaveHistoryRepository leaveHistRepo){
		this.leaveService=leaveService;
		this.leaveHistory=leaveHistory;
		this.userService=userService;
		this.leaveHistRepo=leaveHistRepo;

	}

	public void turnOffAutoApproval(){
		leaveService.disableAutoApproval();
	}

	public void turnOnAutoApproval(){
		leaveService.enableAutoApproval();
	}


	@Override
	public int fetchDetails() {
		Date current = leaveHistory.getFromDate();
		Date end = leaveHistory.getToDate();
		int checkHours=0;
		//String result = prev;
		while (current.before(end)) {
			List<LeaveHistory> leaves = getClashLeaves(leaveHistRepo, current);
			checkHours=checkHours+leaves.size()*8;

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(current);
			calendar.add(Calendar.DATE, 1);
			current = calendar.getTime();
		}

		return checkHours;

	}

	@Override
	public void updateContext() {
		// TODO Auto-generated method stub
		
	}
	protected List<LeaveHistory> getClashLeaves(ILeaveHistoryRepository leaveHistRepo, Date current) {
		return leaveHistRepo.findClashingLeaves(current);
	}
//	public float getParamRequired(String userService, String resource, int depId) {
//		RestTemplate restTemplate = new RestTemplate();
//		String resourceUrl
//		  = userService + "/api/v1/department/" +depId + resource;
//		ResponseEntity<Float> response
//		  = restTemplate.getForEntity(resourceUrl, Float.class);
//		return response.getBody();
//	}

	public void updateTheLimitHours(double scale) {
		RestTemplate restTemplate = new RestTemplate();
		int depId=leaveHistory.getDepartmentId();
		String resourceUrl
				= this.userService + "/api/v1/department/" +depId + "/workHours";
		double updatedHours = restTemplate.getForEntity(resourceUrl, Float.class).getBody();
		updatedHours=updatedHours*scale;
		restTemplate.put(resourceUrl,updatedHours, Float.class);

	}

}
