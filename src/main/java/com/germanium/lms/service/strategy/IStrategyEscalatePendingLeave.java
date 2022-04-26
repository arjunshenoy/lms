package com.germanium.lms.service.strategy;

import org.springframework.web.client.RestTemplate;

import java.util.Date;

public interface IStrategyEscalatePendingLeave {
    public boolean checkPendingLeave(RestTemplate restTemplate, Date fromDate, Date toDate, int employeeId);

}

