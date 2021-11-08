package com.germanium.lms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.germanium.lms.models.Department;

@Repository
public interface IDepartmentRepository extends CrudRepository<Department, Integer> {

}
