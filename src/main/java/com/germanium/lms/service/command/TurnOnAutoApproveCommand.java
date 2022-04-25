package com.germanium.lms.service.command;

public class TurnOnAutoApproveCommand implements ICommand{
	AutoApproveLeave autoApprove;
	public TurnOnAutoApproveCommand(AutoApproveLeave autoApprove) {
		super();
		this.autoApprove = autoApprove;
	}

	@Override
	public void execute() {
		autoApprove.turnOnAutoApprove();;
		
	}
}