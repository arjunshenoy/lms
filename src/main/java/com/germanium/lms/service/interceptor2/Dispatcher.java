package com.germanium.lms.service.interceptor2;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dispatcher implements IDispatcher{
	
	Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	
	public Dispatcher() throws Exception {}
	 
	private List<IHRIntercepter> interceptors = new ArrayList<>();
	@Override
	public void registerInterceptor(IHRIntercepter interceptor) {
		interceptors.add(interceptor);
		
	}

	@Override
	public void removeInterceptor(IHRIntercepter interceptor) {
		interceptors.remove(interceptor);
		
	}

	@Override
	public void dispatch(IContext context) {
		 try {
		        for (IHRIntercepter interceptor : interceptors) {
		            interceptor.callback(context);
		        }
		    } catch(Exception e) {
		    	logger.info("failed to intercept callbacks");
		    }
		
	}

}







