package com.germanium.lms.serviceImpl;

import com.germanium.lms.model.dto.MailRequestDto;
import com.germanium.lms.service.strategy.IStrategyEscalatePendingLeave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class StrategyEscalateManager implements IStrategyEscalatePendingLeave {


    @Value("${user.service.url}")
    private String userService;


    private static final String USER_ENDPOINT = "http://localhost:8081/api/v1/user/profiles/email/";


    @Override
    public boolean checkPendingLeave(RestTemplate restTemplate, Date fromDate, Date toDate, int employeeId) {
        String content = "Leave Application by User Id : " + employeeId + "\n  Leave Details: "
                + "\n" + "FROM_DATE: "+ fromDate+ "\n" + "TO_DATE: " + toDate + "is yet to be approved." + "\n"
                + "Please check and take necessary action";
        String subject = "Reminder - Leave Application approval for- " + employeeId;
        String sendingId = USER_ENDPOINT + employeeId;
        List<String> email_Are = restTemplate.getForObject(sendingId, List.class);
        String[] sendAddress =new String[30];
        sendAddress[0]=email_Are.get(0);
        StrategyEscalator strategyEscalator = new StrategyEscalator();
        strategyEscalator.escalateLeave(restTemplate,content, subject, employeeId, sendAddress);
        return true;
    }
}

