package com.germanium.lms.model.factory;

import java.sql.Blob;

import com.germanium.lms.model.dto.LeaveRequestDto;

public class SickLeave extends Leave {

	private Blob document;

	public SickLeave(LeaveRequestDto active) {
		super(active);
		this.document = active.getDocument();

	}

	@Override
	public Blob getDocument() {
		return document;
	}

	public void setDocument(Blob document) {
		this.document = document;
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

}
