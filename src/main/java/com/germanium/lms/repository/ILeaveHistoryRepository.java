package com.germanium.lms.repository;

import java.util.Date;
<<<<<<< HEAD
import java.util.List;
import java.util.Optional;
=======

>>>>>>> e67e105 (Added combinable leaves support)
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.germanium.lms.model.LeaveHistory;
import com.germanium.lms.model.LeaveHistoryId;

@Repository
public interface ILeaveHistoryRepository extends CrudRepository<LeaveHistory, LeaveHistoryId> {

<<<<<<< HEAD
	Optional<LeaveHistory> findByLeaveId(Integer leaveId);

	Optional<LeaveHistory> findByLeaveHistoryIdLeaveRequestId(Integer leaveRequestId);

	@Query("SELECT leave FROM LeaveHistory leave WHERE :date >= leave.fromDate and :date <= leave.toDate")
	List<LeaveHistory> findClashingLeaves(@Param("date") Date checkDate);

}
=======
	@Query("SELECT leaveHistory FROM LeaveHistory leaveHistory where leaveHistory.FromDate = :fromDate AND leaveHistory.LeaveStatus")
	LeaveHistory findByFromDateAndIsApproved(Date fromDate, String leaveStatus);
}
>>>>>>> e67e105 (Added combinable leaves support)
