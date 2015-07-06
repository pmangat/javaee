package com.javaeesamples.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.javaeesamples.bll.EmployeeService;
import com.javaeesamples.model.Employee;

public class EmployeeServiceTests {

	private static ClassPathXmlApplicationContext context;
	private static EmployeeService empService;

	@BeforeClass
	public static void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("jpaContext-Test.xml");
		context.registerShutdownHook();

		empService = context.getBean(EmployeeService.class);
	}

	@AfterClass
	public static void tearDown() {
		context.close();
	}

	@Test
	public void can_save_and_retrieve_employee() {

		assertThat(empService, notNullValue());

		Employee emp = new Employee();
		emp.setFirstName("Joe");
		emp.setLastName("Root");
		emp.setTitle("Engineer");

		Date dt = Utils.getDate("12/12/2005", "MM/dd/yyyy");
		emp.setDateOfBirth(dt);

		empService.save(emp);

		Employee employee = empService.get(emp.getId());
		assertThat(employee, notNullValue());
		assertThat(employee.getFirstName(), is("Joe"));
		assertThat(employee.getLastName(), is("Root"));
		assertThat(employee.getTitle(), is("Engineer"));
		assertThat(employee.getDateOfBirth(), is(dt));
	}

	@Test
	public void can_delete_employee() {
		assertThat(empService, notNullValue());

		Employee emp = new Employee();
		emp.setFirstName("Joe");
		emp.setLastName("Root");
		emp.setTitle("Engineer");
		Date dt = Utils.getDate("12/12/2005", "MM/dd/yyyy");
		emp.setDateOfBirth(dt);

		empService.save(emp);

		Employee employee = empService.get(emp.getId());
		assertThat(employee, notNullValue());

		empService.delete(employee.getId());

		employee = empService.get(emp.getId());
		assertThat(employee, nullValue());
	}

	@Test
	public void can_update_employee() {
		assertThat(empService, notNullValue());

		Employee emp = new Employee();
		emp.setFirstName("Joe");
		emp.setLastName("Root");
		emp.setTitle("Engineer");
		Date dt = Utils.getDate("12/12/2005", "MM/dd/yyyy");
		emp.setDateOfBirth(dt);

		empService.save(emp);

		Employee employee = empService.get(emp.getId());
		assertThat(employee, notNullValue());
		assertThat(employee.getFirstName(), is("Joe"));
		assertThat(employee.getLastName(), is("Root"));
		assertThat(employee.getTitle(), is("Engineer"));

		employee.setFirstName("Ian");
		employee.setLastName("Bell");
		employee.setTitle("Sr. Engineer");
		dt = Utils.getDate("12/17/2005", "MM/dd/yyyy");
		employee.setDateOfBirth(dt);
		empService.save(employee);

		employee = empService.get(emp.getId());
		assertThat(employee, notNullValue());
		assertThat(employee.getFirstName(), is("Ian"));
		assertThat(employee.getLastName(), is("Bell"));
		assertThat(employee.getTitle(), is("Sr. Engineer"));

		assertThat(employee.getDateOfBirth(), is(dt));

	}

	@Test
	@Category(PagingCategory.class)
	public void can_page_allemployees() {

		assertThat(empService, notNullValue());

		Employee emp = new Employee();
		emp.setFirstName("Joe");
		emp.setLastName("Root");
		emp.setTitle("Engineer");
		Date dt = Utils.getDate("12/12/2005", "MM/dd/yyyy");
		emp.setDateOfBirth(dt);

		empService.save(emp);

		emp = new Employee();
		emp.setFirstName("Ian");
		emp.setLastName("Bell");
		emp.setTitle("Sr. Engineer");
		dt = Utils.getDate("12/07/2005", "MM/dd/yyyy");
		emp.setDateOfBirth(dt);

		empService.save(emp);

		List<Employee> employees = empService.getAll(new PageRequest(0, 1,
				Direction.ASC, new String[] { "LastName" }));
		assertThat(employees, notNullValue());
		assertThat(employees.size(), is(1));
		assertThat(employees.get(0).getFirstName(), is("Ian"));

		employees = empService.getAll(new PageRequest(1, 1, Direction.ASC,
				new String[] { "LastName" }));
		assertThat(employees, notNullValue());
		assertThat(employees.size(), is(1));
		assertThat(employees.get(0).getLastName(), is("Root"));

		employees = empService.getAll(new PageRequest(0, 2, Direction.ASC,
				new String[] { "LastName" }));
		assertThat(employees, notNullValue());
		assertThat(employees.size(), is(2));
		assertThat(employees.get(0).getLastName(), is("Bell"));
		assertThat(employees.get(1).getLastName(), is("Root"));
	}
}
