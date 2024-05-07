package com.Advance.AdvanceTask.service;

import java.util.List;

import com.Advance.AdvanceTask.entity.Employee;

public interface EmployeeService {

	Employee save(Employee theEmployee);

	List<Employee> getEmployee(Employee employee);

	void updateEmployee(Employee tEmployee);

	Employee getEmployeeById(int id);

	List<Employee> duplicateEntry(String email, long mobile);

	Boolean loginEmployee(String email, String password);

	List<Employee> employeeList();

	List<Employee> getEmployeeExcludeCurrentEmail(String email);

	Employee getSingleEmployee(String email);
}
