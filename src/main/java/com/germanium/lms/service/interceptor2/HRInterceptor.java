package com.germanium.lms.service.interceptor2;

public class HRInterceptor implements IHRIntercepter{

	@Override
	public void callback(IContext context) {
		context.fetchContext(context., null);
		
	}

}
