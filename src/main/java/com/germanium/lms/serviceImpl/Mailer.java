package com.germanium.lms.serviceImpl;

import com.germanium.lms.model.dto.MailRequestDto;
import com.germanium.lms.service.bridge.ISendAPI;

import org.springframework.web.client.RestTemplate;


public class Mailer implements ISendAPI {
	private String userService;
	private RestTemplate restTemplate;
	MailRequestDto mailRequest;
	
	private String endPoint;

	public Mailer(int employeeId, String userService, String endPoint) {
		mailRequest = new MailRequestDto();
		mailRequest.setUserId(employeeId);
		this.userService = userService;
		this.endPoint = endPoint;
		this.restTemplate = new RestTemplate();
	}
	public void setSubject(String sub){
		mailRequest.setSubject(sub);
	}
	
	public void setContent(String content) {
		mailRequest.setContent(content);
	}
	
	public void send() {
		restTemplate.postForObject(new StringBuilder(userService).append(endPoint).toString(), mailRequest,
				Boolean.class);
    }
}