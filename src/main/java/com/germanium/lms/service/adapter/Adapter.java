package com.germanium.lms.service.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.repository.IActiveLeaveRepository;

@Service
public class Adapter implements ITarget {
	
	@Autowired
	IActiveLeaveRepository activeLeaveRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	AdapteeMailImpl adapteeMail = new AdapteeMailImpl();
	
	AdapteeNotificationImpl adapteeNotification = new AdapteeNotificationImpl();

	@Override
	public String getSummary(int employeeId, String type) {
		List<ActiveLeaves> activeLeaveList = activeLeaveRepo.findByEmployeeId(employeeId);
		
		if(type.equalsIgnoreCase("mail"))
			return adapteeMail.getSummary(employeeId, activeLeaveList, restTemplate);
		else if(type.equalsIgnoreCase("notification"))
			return adapteeNotification.getSummary(employeeId, activeLeaveList);
		
		return "null";
	}

}
