package com.germanium.lms.serviceImpl;

import com.germanium.lms.service.ILeaveUtilService;
import com.germanium.lms.service.command.ICommand;

public class TurnOnAutoApproveCommand implements ICommand{
	ILeaveUtilService leaveService;
	public TurnOnAutoApproveCommand(ILeaveUtilService leaveService) {
		super();
		this.leaveService = leaveService;
	}

	@Override
	public void execute() {
		leaveService.enableAutoApproval();
		
	}
}