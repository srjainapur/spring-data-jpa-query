package com.java.pagination.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.java.pagination.model.Employee;
import com.java.pagination.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository empRepository;

	public Page<Employee> listEmployee(Pageable pageable) {
		return empRepository.findAll(pageable);
	}
	
	public Employee findEmployeeByFirstNameAndEmail(String firstName, String email) {
		return empRepository.findEmployeeByFirstNameAndEmail(firstName, email);
	}
	
	public Page<Employee> findAllEmployeeWithPagination(Pageable pageable) {
		return empRepository.findAllEmployeeWithPagination(pageable);
	}
	
	public Employee findEmployeeByEmail(String email) {
		return empRepository.findEmployeeByEmail(email);
	}
	
	public Employee findEmployeeByFirstAndLastName(String firstName, String lastName) {
		return empRepository.findEmployeeByFirstAndLastName(firstName, lastName);
	}
	
	public Employee findEmployeeByFirstName(String firstName) {
		return empRepository.findEmployeeByFirstName(firstName);
	}
	
	public Employee findEmployeeByLastNameAndEmail_jpql_1(String lastName, String email) {
		return empRepository.findEmployeeByLastNameAndEmail_jpql_1(lastName, email);
	}
	
	public Employee findEmployeeByLastNameAndEmail_jpql_2(String empLastName, String empEmail) {
		return empRepository.findEmployeeByLastNameAndEmail_jpql_2(empLastName, empEmail);
	}
	
	public Employee findEmployeeByEmailAndFirstName_Native(String empEmail, String empFirstName) {
		return empRepository.findEmployeeByEmailAndFirstName_Native(empEmail, empFirstName);
	}
	
	public List<Employee> findEmployeeByFirstNames(Collection<String> firstNamesCollection) {
		return empRepository.findEmployeeByFirstNames(firstNamesCollection);
	}
	
	public int updateEmployeeEMPNAMEByEmployeeId(String ename, int employeeId) {
		return empRepository.updateEmployeeEMPNAMEByEmployeeId(ename, employeeId);
	}
	
	public int updateEmployeeEMPEMailByEmployeeId(String empEmail, int employeeId) {
		return empRepository.updateEmployeeEMPEMailByEmployeeId(empEmail, employeeId);
	}
	
	public String insertEmployee(String empId, String fname, String lname, String email, Integer salary) {
		empRepository.insertEmployee(empId, fname, lname, email, salary);
		return "Data inserted Successfully";
	}
}
