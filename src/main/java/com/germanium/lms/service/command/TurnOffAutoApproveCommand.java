package com.germanium.lms.service.command;

public class TurnOffAutoApproveCommand implements ICommand {
	AutoApproveLeave autoApprove;
	public TurnOffAutoApproveCommand(AutoApproveLeave autoApprove) {
		super();
		this.autoApprove = autoApprove;
	}

	@Override
	public void execute() {
		autoApprove.turnOffAutoApprove();;
		
	}
}
