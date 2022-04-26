package com.germanium.lms.service.interceptor2;

public class HRInterceptor implements IHRIntercepter{

	@Override
	public void callback(IContext context) {
		int checkHours=context.fetchDetails();
		int hoursLimit=8;
		int hoursLimit2=16;
		int hoursLimit3=24;
		if(checkHours>hoursLimit && checkHours<hoursLimit3){
			context.updateTheLimitHours(0.5);
		}

		if(checkHours<hoursLimit2){
			context.turnOnAutoApproval();
		}
		if(checkHours>hoursLimit3){
			context.turnOffAutoApproval();
		}

	}

}
