package com.germanium.lms.model.factory;

import java.sql.Blob;

import com.germanium.lms.model.dto.LeaveRequestDto;

public class AnnualLeave extends Leave {
	
	private String reason;

	public AnnualLeave(LeaveRequestDto active) {
		super(active);
		this.reason = active.getReason();
	}

	@Override
	public Blob getDocument() {
		return null;
	}
	
	@Override
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public Float getPay() {
		return null;
	}

	@Override
	public Float getPayScale() {
		return null;
	}

	@Override
	public String getComment() {
		return null;
	}
	
	

}
