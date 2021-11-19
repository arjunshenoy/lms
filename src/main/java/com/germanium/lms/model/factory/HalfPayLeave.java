package com.germanium.lms.model.factory;

import java.sql.Blob;

import com.germanium.lms.model.dto.LeaveRequestDto;

public class HalfPayLeave extends Leave {

	private Float pay;

	private Float payScale;

	public HalfPayLeave(LeaveRequestDto active) {
		super(active);
		this.pay = active.getPay();
		this.payScale = active.getPayScale();
	}

	@Override
	public Float getPay() {
		return pay;
	}

	public void setPay(Float pay) {
		this.pay = pay;
	}
	
	@Override
	public Float getPayScale() {
		return payScale;
	}

	public void setPayScale(Float payScale) {
		this.payScale = payScale;
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
	public String getComment() {
		return null;
	}
}
