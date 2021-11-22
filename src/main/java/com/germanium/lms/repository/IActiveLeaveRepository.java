package com.germanium.lms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.germanium.lms.model.ActiveLeaves;

public interface IActiveLeaveRepository extends CrudRepository<ActiveLeaves, Integer> {

	@Query("SELECT activeLeaves FROM ActiveLeaves activeLeaves WHERE activeLeaves.fromDate >= :fromDate AND activeLeaves.toDate <= :toDate"
			+ " AND activeLeaves.departmentId = :id ORDER BY activeLeaves.dateOfApplication ASC")
	List<ActiveLeaves> findByFromDateAndToDateAndDepartmentId(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("id") int departmentId);
}
