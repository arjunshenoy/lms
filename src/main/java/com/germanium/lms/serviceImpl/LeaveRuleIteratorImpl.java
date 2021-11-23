package com.germanium.lms.serviceImpl;

import java.util.List;

import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.service.iterator.Iterator;

public class LeaveRuleIteratorImpl implements Iterator {
	List<LeaveRules> leaveRuleList;
	public LeaveRuleIteratorImpl(List<LeaveRules> leaveRuleList) {
		 this.leaveRuleList = leaveRuleList;
	}
	int pos=0;
	@Override
	public boolean hasNext() {
		
		if (pos <leaveRuleList.size()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Object next() {
		if (this.hasNext()) {
			return leaveRuleList.get(pos++);
		}
		return null;
	}

}
