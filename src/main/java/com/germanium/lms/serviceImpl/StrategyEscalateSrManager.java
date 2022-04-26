package com.germanium.lms.serviceImpl;

import com.germanium.lms.service.strategy.IStrategyEscalatePendingLeave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class StrategyEscalateSrManager implements IStrategyEscalatePendingLeave {

    @Autowired
    StrategyEscalator strategyEscalator;

    @Value("${user.service.url}")
    private String userService;


    private static final String USER_ENDPOINT = "http://localhost:8081/api/v1/user/profiles/email/";
    private static final String MANAGER_DETAILS_ENDPOINT = "http://localhost:8081/api/v1/user/profiles/managers/email";



    @Override
    public boolean checkPendingLeave(RestTemplate restTemplate, Date fromDate, Date toDate, int employeeId) {
        String content = "Leave Application by User Id : " + employeeId + "\n  Leave Details: "
                + "\n" + "FROM_DATE: "+ fromDate+ "\n" + "TO_DATE: " + toDate + "is yet to be approved." + "\n"
                + "Please check and take necessary action";
        String subject = "Reminder - Leave Application approval for- " + employeeId;
        String sendingId =  USER_ENDPOINT + employeeId;
        List<String> email_Are = restTemplate.getForObject(sendingId, List.class);
        //Fetch Senior Manager in the Hierarchy to escalate
        String managersendingId= MANAGER_DETAILS_ENDPOINT + email_Are.get(0);
        List<String> manager_Email = restTemplate.getForObject(managersendingId, List.class);
        String[] sendAddress =new String[30];
        sendAddress[0]=manager_Email.get(0);
        strategyEscalator.escalateLeave(restTemplate,content, subject, employeeId, sendAddress);
        return true;
    }
}
