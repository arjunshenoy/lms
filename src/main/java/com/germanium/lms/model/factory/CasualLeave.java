package com.germanium.lms.model.factory;

import java.sql.Blob;

import com.germanium.lms.model.dto.LeaveRequestDto;

public class CasualLeave extends Leave {

	public CasualLeave(LeaveRequestDto active) {
		super(active);
	}

	@Override
	public Blob getDocument() {
		return null;
	}

	@Override
	public String getReason() {
		return null;
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
