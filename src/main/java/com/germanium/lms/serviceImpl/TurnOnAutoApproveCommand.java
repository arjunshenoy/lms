package com.germanium.lms.serviceImpl;

import com.germanium.lms.service.ILeaveService;
import com.germanium.lms.service.command.ICommand;

public class TurnOnAutoApproveCommand implements ICommand{
	ILeaveService leaveService;
	public TurnOnAutoApproveCommand(ILeaveService leaveService) {
		super();
		this.leaveService = leaveService;
	}

	@Override
	public void execute() {
		leaveService.enableAutoApproval();
		
	}
}