package com.germanium.lms.serviceImpl;

import com.germanium.lms.service.bridge.ISendAPI;
import com.germanium.lms.service.bridge.Notifier;

public class NotifyLeaveFormal extends Notifier {
	boolean success;
    public NotifyLeaveFormal(String sub, ISendAPI sender, boolean success) {
        super(sub, sender);
		this.success = success;
    }

	public String buildMsg() {
		return "";
	}
}