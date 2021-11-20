package com.germanium.lms.model.factory;

import com.germanium.lms.model.dto.LeaveRequestDto;

public class LeaveFactory {

	public static Leave getNewLeaveObject(LeaveRequestDto activeLeave) {
		if (activeLeave.getLeaveName().equalsIgnoreCase("SICK LEAVE")) {
			return new SickLeave(activeLeave);
		}
		if (activeLeave.getLeaveName().equalsIgnoreCase("CASUAL LEAVE")) {
			return new CasualLeave(activeLeave);
		}
		if (activeLeave.getLeaveName().equalsIgnoreCase("ANNUAL LEAVE")) {
			return new AnnualLeave(activeLeave);
		}
		if (activeLeave.getLeaveName().equalsIgnoreCase("HALF PAY LEAVE")) {
			return new HalfPayLeave(activeLeave);
		}

		return null;
	}
	
	private LeaveFactory() {
		throw new IllegalStateException("Factory class");
	}
}
