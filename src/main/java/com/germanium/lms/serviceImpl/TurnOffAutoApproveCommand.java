package com.germanium.lms.serviceImpl;

import com.germanium.lms.service.ILeaveUtilService;
import com.germanium.lms.service.command.ICommand;

public class TurnOffAutoApproveCommand implements ICommand {
	ILeaveUtilService leaveService;
	public TurnOffAutoApproveCommand(ILeaveUtilService leaveService) {
		super();
		this.leaveService = leaveService;
	}

	@Override
	public void execute() {
		leaveService.disableAutoApproval();
		
	}

	@Override
	public void undo() {
		leaveService.enableAutoApproval();
		
	}
}
