package com.germanium.lms.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.LeaveHistoryId;

@Repository
public interface ILeaveHistoryRepository extends CrudRepository<LeaveHistory, LeaveHistoryId> {

	Optional<LeaveHistory> findByLeaveId(Integer leaveId);

	Optional<LeaveHistory> findByLeaveHistoryIdLeaveRequestId(Integer leaveRequestId);

}
