package com.germanium.lms.serviceImpl;

import com.germanium.lms.service.bridge.ISendAPI;

public class PrintNotify implements ISendAPI {
	String msg;
	public PrintNotify(int id) {
		msg+="Message<"+id+"@";
	}
	public void setSubject(String sub){
		msg += sub + ">";
	}
	
	public void setContent(String content) {
		msg += content;
	}
	
	public void send() {
        System.out.println(msg);
    }
}
