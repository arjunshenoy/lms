package com.germanium.lms.serviceImpl;

import com.germanium.lms.model.dto.MailRequestDto;
import com.germanium.lms.service.bridge.ISendAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class Mailer implements ISendAPI {
	@Value("${user.service.url}")
	private String userService;
	@Autowired
	private RestTemplate restTemplate;
	MailRequestDto mailRequest;
	
	private static final String NOTIFY_EMAIL_ENDPOINT = "/mail/leave/notify";

	public Mailer(int employeeId) {
		mailRequest = new MailRequestDto();
		mailRequest.setUserId(employeeId);
	}
	public void setSubject(String sub){
		mailRequest.setSubject(sub);
	}
	
	public void setContent(String content) {
		mailRequest.setContent(content);
	}
	
	public void send() {
		restTemplate.postForObject(new StringBuilder(userService).append(NOTIFY_EMAIL_ENDPOINT).toString(), mailRequest,
				Boolean.class);
    }
}