package com.germanium.lms.serviceImpl;

import com.germanium.lms.service.bridge.ISendAPI;
import org.slf4j.Logger;

public class LogNotify implements ISendAPI {
	String sub;
	String content;
	int id;
	Logger logger;
	boolean error;
	
	public LogNotify(Logger logger, int employeeId, boolean error) {
		this.logger = logger;
		this.id = employeeId;
		this.error = error;
	}
	
	public void setSubject(String sub){
		this.sub = sub;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void send() {
		String msg = "<Notification "+id+"@"+this.sub+"> "+content;
		if (error) {
			logger.error(msg);
			return;
		}
        logger.info(msg);
    }
}
