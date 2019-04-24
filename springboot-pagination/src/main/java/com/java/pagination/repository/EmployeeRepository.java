package com.java.pagination.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.java.pagination.model.Employee;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer> {
	
	@Query(value="SELECT E FROM Employee E WHERE E.firstName = ?1 AND E.email = ?2")
	public Employee findEmployeeByFirstNameAndEmail(String firstName, String email);
	
	// Native Query with pagination
	/* We can enable pagination for native queries by declaring an additional 
	 * attribute countQuery — this defines the SQL to execute 
	 * to count the number of rows in the whole result:
	 */
	@Query(value="SELECT * FROM EMPLOYEE ORDER BY EMPLOYEE_ID ASC",
			//countQuery="SELECT COUNT(*) FROM EMPLOYEE",
			nativeQuery=true
			)
	public Page<Employee> findAllEmployeeWithPagination(Pageable pageable);
	
	
	/****************************** BEGIN :: Indexed Query Parameter *****************************************/
	
	/* Passing parameters to Query
	 * Indexed Query Parameter
	 * 
	 * For indexed parameters in JPQL, Spring Data will pass 
	 * method parameters to the query in the same order they 
	 * appear in the method declaration:
	 */
	@Query(value="SELECT E FROM Employee E WHERE E.email = ?1")
	public Employee findEmployeeByEmail(String email);
	
	@Query(value="SELECT E FROM Employee E WHERE E.firstName = ?1 AND E.lastName = ?2")
	public Employee findEmployeeByFirstAndLastName(String firstName, String lastName);
	
	/* For the above queries, 
	 * the firstName method parameter will be assigned to the query parameter with index 1, 
	 * the lastName method parameter will be assigned to the query parameter with index 2.
	 */
	
	/* Native Query 
	 * 
	 * Indexed parameters for the native queries work exactly in the same way as for JPQL:
	 */
	@Query(value="SELECT * FROM EMPLOYEE E WHERE E.FIRST_NAME = ?1", 
			nativeQuery=true)
	public Employee findEmployeeByFirstName(String firstName);
	
	/****************************** END :: Indexed Query Parameter *****************************************/
	
	/************************************ BEGIN :: Named Parameters ***************************************/
	/* We can also pass method parameters to the query using named parameters. 
	 * We define these using the @Param annotation inside our repository 
	 * method declaration.
	 * 
	 * Each parameter annotated with @Param must have a value string matching 
	 * the corresponding JPQL or SQL query parameter name. A query with named 
	 * parameters is easier to read and is less error-prone in case the query 
	 * needs to be refactored. 
	 */
	
	/************** JPQL **************/
	@Query(value="SELECT E FROM Employee E WHERE E.lastName = :lastName AND E.email = :email")
	public Employee findEmployeeByLastNameAndEmail_jpql_1(@Param("lastName") String lastName,  @Param("email") String email);
	
	
	/* Note that in the above example, we defined our SQL query and method parameters to 
	 * have the same names, but it’s not required, as long as the value strings are the same:
	 */
	@Query(value="SELECT E FROM Employee E WHERE E.lastName = :lastName AND E.email = :email")
	public Employee findEmployeeByLastNameAndEmail_jpql_2(@Param("lastName") String empLastName,  @Param("email") String empEmail);
	
	/******************	Native *******************/
	
	/* For the native query definition, there is no difference how we 
	 * pass a parameter via the name to the query in comparison to JPQL 
	 * — we use the @Param annotation:
	 */
	@Query(value="SELECT * FROM EMPLOYEE E WHERE E.EMAIL = :email AND E.FIRST_NAME = :firstName", 
			nativeQuery=true)
	public Employee findEmployeeByEmailAndFirstName_Native(@Param("email") String empEmail, @Param("firstName") String empFirstName);
	
	/************************************ END :: Named Parameters ***************************************/
	
	
	
	/******************BEGIN :: Collection Parameter ************************/
	/* Let’s consider the case where where clause of our JPQL or 
	 * SQL query contains the IN (or NOT IN) keyword: 
	 * 
	 * SELECT E FROM Employee E WHERE E.firstName IN :firstNames
	 * 
	 * In this case we can define a query method which takes 
	 * Collection as a parameter:
	 * 
	 * As the parameter is a Collection it can be used with List, HashSet, etc.
	 * 
	 *              JPQL Query        
	 */
	@Query(value="SELECT E FROM Employee E WHERE E.firstName IN :firstNames")
	public List<Employee> findEmployeeByFirstNames(@Param("firstNames") Collection<String> firstNamesCollection);
	
	
	/******************END :: Collection Parameter ************************/
	
	
	/******************BEGIN :: @Modifying ****************************************/
	
	/* We can use the @Query annotation to modify the state of the database by 
	 * also adding the @Modifying annotation to the repository method.
	 * 
	 */
	
	/************************ JPQL **************************************/
	/* The repository method that modifies the data has two difference in 
	 * comparison to the select query — it has the @Modifying annotation 
	 * and, of course, the JPQL query uses update instead of select:
	 * 
	 * The return value defines how many rows the execution of the query updated. Both indexed and named parameters can be used inside update queries.
	 * 
	 * If you don't put @Transactional annotation then you will get following exception
	 * 
	 * javax.persistence.TransactionRequiredException: Executing an update/delete query
		at org.hibernate.internal.AbstractSharedSessionContract.checkTransactionNeededForUpdateOperation(AbstractSharedSessionContract.java:398) ~[hibernate-core-5.3.9.Final.jar:5.3.9.Final]
		at org.hibernate.query.internal.AbstractProducedQuery.executeUpdate(AbstractProducedQuery.java:1585) ~[hibernate-core-5.3.9.Final.jar:5.3.9.Final]
		at org.springframework.data.jpa.repository.query.JpaQueryExecution$ModifyingExecution.doExecute(JpaQueryExecution.java:256) ~[spring-data-jpa-2.1.6.RELEASE.jar:2.1.6.RELEASE]
	 * 
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Employee E SET E.empName = :empName WHERE E.empId = :empId")
	public int updateEmployeeEMPNAMEByEmployeeId(@Param("empName") String ename, @Param("empId") int employeeId);
	/******************END :: @Modifying ****************************************/
	
	/***************** Native *******************/
	/* We can modify the state of the database also with a native query — 
	 * we just need to add the @Modifying annotation:
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE EMPLOYEE E SET E.EMP_EMAIL = :empEmail WHERE E.EMPLOYEE_ID = :empId",
			nativeQuery=true)
	public int updateEmployeeEMPEMailByEmployeeId(@Param("empEmail") String empEmail, @Param("empId") int employeeId);
	
	/***************************************************************************/
	
	
	/*****************************BEGIN Inserts *************************************/
	/* To perform an insert operation, we have to both apply 
	 * @Modifying and use a native query since INSERT is not a part of the JPA interface:
	 */
	@Modifying
	@Transactional
	@Query(value="INSERT INTO EMPLOYEE(EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, SALARY) VALUES(:employee_id, :first_name, :last_name, :email, :salary)", nativeQuery=true)
	public void insertEmployee(@Param("employee_id") String empId, @Param("first_name") String fname, 
		@Param("last_name") String lname, @Param("email") String email, @Param("salary") Integer salary);
	
	/*****************************END Inserts *************************************/
}
