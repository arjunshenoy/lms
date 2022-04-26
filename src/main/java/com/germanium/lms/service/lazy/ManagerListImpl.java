package com.germanium.lms.service.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.germanium.lms.model.dto.Manager;

public class ManagerListImpl implements ManagerList {

	List<Manager> managersList = new ArrayList<>();

	@Override
	public List<Manager> getManagerList(String departmentName) {
		return getManagerListBasedOnDepartment(departmentName);
	}

	private List<Manager> getManagerListBasedOnDepartment(String departmentName) {
		List<Manager> managersList = new ArrayList<>();
		managersList.add(new Manager(1, "Computer", "Arjun"));
		managersList.add(new Manager(2, "Electronics", "Arjun"));
		managersList.add(new Manager(3, "Computer", "Arjun"));
		managersList.add(new Manager(4, "Computer", "Arjun"));
		return managersList.stream().filter(manager -> manager.getDepartment().equalsIgnoreCase(departmentName))
				.collect(Collectors.toList());
	}

}
