package com.germanium.lms.repository;

import org.springframework.data.repository.CrudRepository;

import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.LeaveHistoryId;

public interface ILeaveHistoryRepository extends CrudRepository<LeaveHistory, LeaveHistoryId> {

}
