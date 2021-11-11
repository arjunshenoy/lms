package com.germanium.lms.model.factory;

import java.sql.Blob;

import com.germanium.lms.model.dto.LeaveRequestDto;

public class CasualLeave extends Leave {

	public CasualLeave(LeaveRequestDto active) {
		super(active);
	}

	@Override
	public Blob getDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReason() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float getPay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float getPayScale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getComment() {
		// TODO Auto-generated method stub
		return null;
	}

}
