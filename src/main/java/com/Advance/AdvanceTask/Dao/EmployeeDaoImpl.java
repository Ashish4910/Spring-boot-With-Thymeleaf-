package com.Advance.AdvanceTask.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Advance.AdvanceTask.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public Employee save(Employee theEmployee) {

		Employee employee = entityManager.merge(theEmployee);

		return employee;
	}

	@Override
	public List<Employee> getEmployee(Employee employee) {

		TypedQuery<Employee> theQuery = entityManager.createQuery("SELECT a from Employee a", Employee.class);

		// List<Employee> emp = theQuery.getResultList();
		return theQuery.getResultList();
	}

	@Override
	public Employee getEmployeeById(int id) {

		Query query = entityManager.createQuery("Select e from Employee e where e.id = :id");
		query.setParameter("id", id);

		return (Employee) query.getSingleResult();
	}

	@Override
	public List<Employee> duplicateEntry(String email, long mobile) {
		Query query = entityManager
				.createQuery("SELECT e FROM Employee e WHERE e.email = :email OR e.mobile = :mobile");
		query.setParameter("email", email);
		query.setParameter("mobile", String.valueOf(mobile));

		List<Employee> duplicateEntries = query.getResultList();

		System.out.println("Duplicate Entries: " + duplicateEntries);

		return duplicateEntries;
	}

	@Override
	public boolean loginEmployee(String email, String password) {

		Query query = entityManager
				.createQuery("select e from Employee e where e.email=:email and e.password=:password");

		query.setParameter("email", email);
		query.setParameter("password", password);

		List<Employee> theEmployees = query.getResultList();

		if (!theEmployees.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<Employee> employeeList() {

		Query query = entityManager.createQuery("Select e from Employee e ");

		List<Employee> employees = query.getResultList();

		return employees;
	}

	@Override
	public List<Employee> getEmployeeExcludeCurrentEmail(String email) {

		Query query = entityManager.createQuery("select e from Employee e where e.email != :email");

		query.setParameter("email", email);

		List<Employee> employees = query.getResultList();

		return employees;
	}

	@Override
	public Employee getSingleEmployee(String email) {
		Query query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.email = :email");
		query.setParameter("email", email);
		try {
			return (Employee) query.getSingleResult();
		} catch (NoResultException e) {
			return null; // Return null if no employee is found with the given email
		} catch (NonUniqueResultException e) {
			throw new NonUniqueResultException("Multiple employees found for email: " + email);
		}
	}

	@Transactional
	@Override
	public void updateEmployee(Employee employee) {

		Query query = entityManager.createQuery(
				"Update Employee e set   e.password=:password, e.name= :name, e.gender=:gender, e.mobile=:mobile  where e.email=:email");

//		query.setParameter("email", employee.getEmail());
		query.setParameter("password", employee.getPassword());
		query.setParameter("name", employee.getName());
		query.setParameter("gender", employee.getGender());
		query.setParameter("mobile", employee.getMobile());
		query.setParameter("email", employee.getEmail());

		int row = query.executeUpdate();

		if (row > 0) {
			System.out.println("Employee Details is Updated Successfully");
		} else {
			System.out.println("Employee Details failed to Update");
		}
	}

}
