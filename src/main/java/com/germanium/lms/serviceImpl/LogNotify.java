package com.germanium.lms.serviceImpl;

import com.germanium.lms.service.bridge.ISendAPI;
import com.germanium.lms.service.interceptor.InterceptibleFramework;
import com.germanium.lms.service.interceptor.Interceptor;
import com.germanium.lms.service.interceptor.InterceptorConcrete;
import com.germanium.lms.service.interceptor.annotation.*;

import org.slf4j.Logger;


public class LogNotify extends InterceptibleFramework implements ISendAPI {
	String sub;
	String content;
	@Accessible(event = { "empid" })
	int id;
	Logger logger;
	@Mutable(event = { "logerror" })
	boolean error;
	
	public LogNotify(Logger logger, int employeeId, boolean error) {
		this.logger = logger;
		this.id = employeeId;
		this.error = error;
		Interceptor i = new InterceptorConcrete();
		this.setInterceptor(i, "lognotify");
	}
	
	public void setSubject(String sub){
		this.sub = sub;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Interceptible(event = "lognotify")
	public void send() {
		String msg = "<Notification "+id+"@"+this.sub+"> "+content;
		if (error) {
			logger.error(msg);
			return;
		}
        logger.info(msg);
    }
}
