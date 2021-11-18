package com.germanium.lms.repository;

import org.springframework.data.repository.CrudRepository;

import com.germanium.lms.model.ActiveLeaves;

public interface IActiveLeaveRepository extends CrudRepository<ActiveLeaves, Integer> {

}
