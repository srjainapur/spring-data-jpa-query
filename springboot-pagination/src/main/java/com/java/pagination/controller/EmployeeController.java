package com.java.pagination.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.pagination.model.Employee;
import com.java.pagination.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService;
	
	@RequestMapping(value="/listEmp", method=RequestMethod.GET)
	public Page<Employee> listEmployee(Pageable pageable) {
		return empService.listEmployee(pageable);
	}
	
	@RequestMapping(value="/byNameAndEmail/{name}/{email}", method=RequestMethod.GET)
	public Employee findEmployeeByFirstNameAndEmail(@PathVariable("name") String firstName, @PathVariable("email") String email) {
		return empService.findEmployeeByFirstNameAndEmail(firstName, email);
	}
	
	@RequestMapping(value="/pagination", method=RequestMethod.GET)
	public Page<Employee> findAllEmployeeWithPagination(Pageable pageable) {
		return empService.findAllEmployeeWithPagination(pageable);
	}
	
	@RequestMapping(value="/byEmial/{email}", method=RequestMethod.GET)
	public Employee findEmployeeByEmail(@PathVariable("email") String email) {
		return empService.findEmployeeByEmail(email);
	}
	
	@RequestMapping(value="/byName/{firstName}/{lastName}", method=RequestMethod.GET)
	public Employee findEmployeeByFirstAndLastName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
		return empService.findEmployeeByFirstAndLastName(firstName, lastName);
	}
	
	@RequestMapping(value="/byFirstName/{firstName}", method=RequestMethod.GET)
	public Employee findEmployeeByFirstName(@PathVariable("firstName") String firstName) {
		return empService.findEmployeeByFirstName(firstName);
	}
	
	@RequestMapping(value="/byLastNameAndEmailJPQL1/{lastName}/{email}", method=RequestMethod.GET)
	public Employee findEmployeeByLastNameAndEmail_jpql_1(@PathVariable("lastName") String lastName, 
			@PathVariable("email") String email) {
		
		return empService.findEmployeeByLastNameAndEmail_jpql_1(lastName, email);
	}
	
	@RequestMapping(value="/byLastNameAndEmailJPQL2/{lastName}/{email}", method=RequestMethod.GET)
	public Employee findEmployeeByLastNameAndEmail_jpql_2(@PathVariable("lastName") String empLastName, 
		@PathVariable("email") String empEmail) {
		
		return empService.findEmployeeByLastNameAndEmail_jpql_2(empLastName, empEmail);
	}
	
	@RequestMapping(value="/byEmailAndFirstNameNative/{email}/{firstName}", method=RequestMethod.GET)
	public Employee findEmployeeByEmailAndFirstName_Native(@PathVariable("email") String empEmail, @PathVariable("firstName") String empFirstName) {
		return empService.findEmployeeByEmailAndFirstName_Native(empEmail, empFirstName);
	}
	
	@RequestMapping(value="/getEmployeesByFirstNames", method=RequestMethod.GET)
	public List<Employee> findEmployeeByFirstNames(@RequestParam("firstNames") Collection<String> firstNamesCollection) {
		return empService.findEmployeeByFirstNames(firstNamesCollection);
	}
	
	@RequestMapping(value="/updateEmployeeByEmpId/{empName}/{employeeId}", method=RequestMethod.PUT)
	public int updateEmployeeEMPNAMEByEmployeeId(@PathVariable("empName") String ename, @PathVariable("employeeId") int employeeId) {
		return empService.updateEmployeeEMPNAMEByEmployeeId(ename, employeeId);
	}
	
	@RequestMapping(value="/updateEmployeeEmailByEmpId/{empEmail}/{employeeId}", method=RequestMethod.PUT)
	public int updateEmployeeEMPEMailByEmployeeId(@PathVariable("empEmail") String empEmail, @PathVariable("employeeId") int employeeId) {
		return empService.updateEmployeeEMPEMailByEmployeeId(empEmail, employeeId);
	}
	
	@RequestMapping(value="/insertEmployee/{empId}/{firstName}/{lastName}/{email}/{salary}", method=RequestMethod.PUT)
	public String insertEmployee(@PathVariable("empId") String empId, @PathVariable("firstName") String fname, 
			@PathVariable("lastName") String lname, @PathVariable("email") String email, @PathVariable("salary") int salary) {
		return empService.insertEmployee(empId, fname, lname, email, salary);		
	}
}
