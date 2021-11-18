package com.germanium.lms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.germanium.lms.model.LeaveRules;

@Repository
public interface ILeaveRulesRepository extends CrudRepository<LeaveRules, Integer> {

	LeaveRules findByName(String leaveType);

}
