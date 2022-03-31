package com.germanium.lms.serviceImpl;

import org.springframework.stereotype.Service;

import com.germanium.lms.service.command.ICommand;

@Service
public class AutoApproveInvoker {
	ICommand command;
	
	public void setCommand(ICommand command) {
	    this.command = command;
	  }
	 
	  public void buttonPressed() {
	    command.execute();
	  }
}
