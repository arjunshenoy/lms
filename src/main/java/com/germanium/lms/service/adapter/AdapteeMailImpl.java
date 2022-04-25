package com.germanium.lms.service.adapter;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.germanium.lms.model.ActiveLeaves;
import com.germanium.lms.model.dto.MailRequestDto;

@Service
public class AdapteeMailImpl {

	private String userService = "http://localhost:8081";

	private static final String NOTIFY_EMAIL_ENDPOINT = "/mail/leave/notify";

	ObjectMapper mapper = new ObjectMapper();

	private static final Logger logger = LoggerFactory.getLogger(AdapteeMailImpl.class);

	public String getSummary(int employeeId, List<ActiveLeaves> activeLeaveList, RestTemplate restTemplate) {
		logger.info("Sending leave details as mail to employee {}", employeeId);
		String content = "";
		try {
			content = mapper.writeValueAsString(activeLeaveList);
		} catch (JsonProcessingException e) {
			return "Error in mapping object to string";
		}
		String subject = "Leave Details";
		sendMail(content, subject, employeeId, restTemplate);
		return "Mail sent successfully";
	}

	private void sendMail(String content, String subject, int employeeId, RestTemplate restTemplate) {
		MailRequestDto mailRequest = new MailRequestDto();
		mailRequest.setContent(content);
		mailRequest.setSubject(subject);
		mailRequest.setUserId(employeeId);
		try {
			restTemplate.postForObject(new StringBuilder(userService).append(NOTIFY_EMAIL_ENDPOINT).toString(),
					mailRequest, Boolean.class);
		} catch (Exception e) {
			System.out.println( "Error in connecting to user service");
		}
	}
}
