package com.Advance.AdvanceTask.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Advance.AdvanceTask.Dao.EmployeeDao;
import com.Advance.AdvanceTask.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	public EmployeeDao employeeDao;

	@Autowired
	public EmployeeServiceImpl(EmployeeDao theEmployeeDao) {

		this.employeeDao = theEmployeeDao;
	}

	@Override
	public Employee save(Employee theEmployee) {

		return employeeDao.save(theEmployee);
	}

	@Override
	public List<Employee> getEmployee(Employee employee) {

		return employeeDao.getEmployee(employee);
	}

	@Override
	public Employee getEmployeeById(int id) {

		return employeeDao.getEmployeeById(id);
	}

	@Override
	public List<Employee> duplicateEntry(String email, long mobile) {

		return employeeDao.duplicateEntry(email, mobile);
	}

	@Override
	public Boolean loginEmployee(String email, String password) {
		// TODO Auto-generated method stub
		return employeeDao.loginEmployee(email, password);
	}

	@Override
	public List<Employee> employeeList() {

		return employeeDao.employeeList();
	}

	@Override
	public List<Employee> getEmployeeExcludeCurrentEmail(String email) {

		return employeeDao.getEmployeeExcludeCurrentEmail(email);
	}

	@Override
	public Employee getSingleEmployee(String email) {

		return employeeDao.getSingleEmployee(email);
	}

	@Override
	public void updateEmployee(Employee tEmployee) {
		employeeDao.updateEmployee(tEmployee);

	}

}
