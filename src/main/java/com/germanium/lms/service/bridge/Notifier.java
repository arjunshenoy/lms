package com.germanium.lms.service.bridge;

public abstract class Notifier {
    protected ISendAPI sender;
	
    protected Notifier(String sub, ISendAPI sender) {
        this.sender = sender;
		sender.setSubject(sub);
    }
	
	public abstract String buildMsg();
	
    public void send() {
		sender.setContent(buildMsg());
		sender.send();
	}
}