package com.germanium.lms.serviceImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.germanium.lms.model.factory.Leave;
import com.germanium.lms.service.decorator.IAutoApprove;

public class AutoApproveQueue implements IAutoApprove {
	
	private static final String QUEUE ="queue";
	@Value("${user.service.url}")
	private String userService = "http://user-service:8081";

	@Override
	public String checkApprovalRule(Leave leaveRequest, String prev) {
		int depId = leaveRequest.getDepartmentId();
		Date fromDate = leaveRequest.getFromDate();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date currentDate = Date.from(LocalDate.now().atStartOfDay(defaultZoneId).toInstant());

		String result = "approve";
		if (fromDate.before(currentDate)) {
			result = QUEUE;
		} else {

			long diffInMillies = Math.abs(currentDate.getTime() - fromDate.getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;

			if (diff >= getQueueDays(depId))
				result = QUEUE;
		}
		if (result.equals(QUEUE) && prev.equals(QUEUE))
			return result;
		return prev;

	}

	public float getQueueDays(int depId) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = userService + "/api/v1/department/" +depId + "/leavequeue";
		ResponseEntity<Integer> response = restTemplate.getForEntity(resourceUrl, Integer.class);
		return response.getBody();
	}

}
