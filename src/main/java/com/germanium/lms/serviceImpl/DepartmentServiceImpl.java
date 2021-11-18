package com.germanium.lms.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.germanium.lms.model.Department;
import com.germanium.lms.repository.IDepartmentRepository;
import com.germanium.lms.service.IDepartmentService;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
	
	Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);
	
	@Autowired
	IDepartmentRepository departmentRepo;
	
	@Override
	public Department createDepartment(Department department) {
		logger.info("Creating Department");
		return departmentRepo.save(department);	
	}

	@Override
	public List<Department> getDepartments() {
		logger.info("Getting the Department Details");
		return (List<Department>) departmentRepo.findAll();
	}
	
	@Override
	public Department findDepartmentById(Integer departmentId) throws Exception {
		logger.info("Getting the Department Details for Department Id: "+departmentId);
		Optional<Department> optionalDepartment = departmentRepo.findById(departmentId);
		if (!optionalDepartment.isPresent()) {
			throw new Exception("Department With Department Id: "+ departmentId+ " Not Found");
		}
		return optionalDepartment.get();
	}

	@Override
	public Department updateDepartment(Integer departmentId, Department department) throws Exception {
		if(!departmentRepo.existsById(departmentId)) {
			throw new Exception("Department With Department Id: "+ departmentId+ " Not Found");
		}
		logger.info("Updating Department Details");
		return departmentRepo.save(department);
	}

	@Override
	public boolean deleteDepartment(Integer departmentId) throws Exception {
		logger.info("Deleting Department Details");
		if (!departmentRepo.existsById(departmentId)) {
			throw new Exception("Department With Department Id: "+ departmentId+ " Not Found");
		}
		departmentRepo.deleteById(departmentId);
		return true;
	}

	
}
