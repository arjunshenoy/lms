package com.germanium.lms.serviceImpl;

import com.germanium.lms.service.ILeaveService;
import com.germanium.lms.service.command.ICommand;

public class TurnOffAutoApproveCommand implements ICommand {
	ILeaveService leaveService;
	public TurnOffAutoApproveCommand(ILeaveService leaveService) {
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
