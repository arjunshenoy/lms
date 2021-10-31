package com.germanium.lms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.germanium.lms.models.LeaveRules;
import com.germanium.lms.repository.ILeaveRulesRepository;


@Service
public class LeaveServiceImpl implements ILeaveService {
	
	@Autowired
	ILeaveRulesRepository leaveRulesRepo;

	@Override
	public List<LeaveRules> getLeaveRules() {
				
		return (List<LeaveRules>) leaveRulesRepo.findAll();
	}

	@Override
	public LeaveRules findLeavesById(Integer leaveId) throws Exception {
		Optional<LeaveRules> optionalLeave = leaveRulesRepo.findById(leaveId);
		if (!optionalLeave.isPresent() ) {
			throw new Exception("Leave With Leave Id: Not Found " + leaveId);
		}
		return optionalLeave.get();
	}

	@Override
	public List<LeaveRules> createLeaveRules(List<LeaveRules> leaveType) {
		return (List<LeaveRules>) leaveRulesRepo.saveAll(leaveType);
	}

	@Override
	public void updateLeaveRules(Integer leaveId, LeaveRules leaveRule) throws Exception {
		if (!leaveRulesRepo.existsById(leaveId)) {
			throw new Exception("Leave With Leave Id: Not Found " + leaveId);
		}
		leaveRulesRepo.save(leaveRule);
		
	}

	@Override
	public void deleteLeaveRules(Integer leaveId) throws Exception {
		if (!leaveRulesRepo.existsById(leaveId)) {
			throw new Exception("Leave With Leave Id: Not Found " + leaveId);
		}
		leaveRulesRepo.deleteById(leaveId);
	}
	

}
