package com.germanium.lms.service.bridge;

public interface ISendAPI {
	void setSubject(String sub);
	void setContent(String content);
	void send();
}
