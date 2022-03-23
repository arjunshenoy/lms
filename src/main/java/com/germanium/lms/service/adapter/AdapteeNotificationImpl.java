package com.germanium.lms.service.adapter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.germanium.lms.model.ActiveLeaves;

@Service
public class AdapteeNotificationImpl {

	private static final Logger logger = LoggerFactory.getLogger(AdapteeNotificationImpl.class);
	ObjectMapper mapper = new ObjectMapper();

	public String getSummary(int employeeId, List<ActiveLeaves> activeLeaveList) {
		logger.info("Printing notification for {}", employeeId);
		try {
			return mapper.writeValueAsString(activeLeaveList);
		} catch (Exception e) {
			return "Error in mapping object to string";
		}
	}
}
