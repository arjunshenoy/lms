package com.germanium.lms.serviceImpl;

import com.germanium.lms.model.dto.MailRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class StrategyEscalator {

    @Value("${user.service.url}")
    private String userService;

    private static final String NOTIFY_EMAIL_ENDPOINT = "http://localhost:8081/mail/send";

    public void escalateLeave(RestTemplate restTemplate,String content, String subject, int employeeId, String[] sendAddress) {
        MailRequestDto mailRequest = new MailRequestDto();
        mailRequest.setContent(content);
        mailRequest.setSubject(subject);
        mailRequest.setUserId(employeeId);
        mailRequest.setToAddress(sendAddress);
        restTemplate.postForObject(NOTIFY_EMAIL_ENDPOINT, mailRequest,
                Boolean.class);
    }

}
