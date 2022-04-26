package com.germanium.lms.service.adapter;

import org.springframework.stereotype.Service;

@Service
public interface ITarget {
	
	public String getSummary(int employee, String type);
}
