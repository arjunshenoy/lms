package com.germanium.lms.service.interceptor;

import com.germanium.lms.service.interceptor.context.Context;

public class InterceptorConcrete implements Interceptor {

	@Override
	public void callback(Context context) {
		int id = (int) context.getValue("empid");
		if (id == 1) {
			context.setValue("logerror", true);
		}
	}

}
