package com.germanium.lms.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.LeaveHistoryId;

@Repository
public interface ILeaveHistoryRepository extends CrudRepository<LeaveHistory, LeaveHistoryId> {

	Optional<LeaveHistory> findByLeaveId(Integer leaveId);

	Optional<LeaveHistory> findByLeaveHistoryIdLeaveRequestId(Integer leaveRequestId);

	@Query("SELECT leave FROM LeaveHistory leave WHERE :date >= leave.fromDate and :date <= leave.toDate")
	List<LeaveHistory> findClashingLeaves(@Param("date") Date checkDate);

}