package com.germanium.lms.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.germanium.lms.model.LeaveRules;
import com.germanium.lms.repository.ILeaveRulesRepository;
import com.germanium.lms.service.iterator.Collection;
import com.germanium.lms.service.iterator.Iterator;


public class LeaveRuleCollectionImpl implements Collection {
	
	List<LeaveRules> leaveRuleList =new ArrayList<LeaveRules>();
	public LeaveRuleCollectionImpl(ILeaveRulesRepository leaveRulesRepo) {
		List<LeaveRules> leaveRules = (List<LeaveRules>) leaveRulesRepo.findAll();
		this.leaveRuleList = leaveRules;
	}
	public LeaveRuleCollectionImpl(ILeaveRulesRepository leaveRulesRepo,String date) {
		List<LeaveRules> leaveRules =  leaveRulesRepo.findByLapseDate(date);
		this.leaveRuleList = leaveRules;
	}
	@Override
	public Iterator getIterator() {
		return new LeaveRuleIteratorImpl(this.leaveRuleList);
	}
}
