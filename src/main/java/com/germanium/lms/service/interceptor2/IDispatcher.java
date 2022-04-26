package com.germanium.lms.service.interceptor2;

public interface IDispatcher {
	public void registerInterceptor(IHRIntercepter interceptor);
	public void removeInterceptor(IHRIntercepter interceptor);
	public void dispatch(IContext context);
}
