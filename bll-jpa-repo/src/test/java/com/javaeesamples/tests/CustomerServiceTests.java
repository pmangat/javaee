package com.javaeesamples.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.javaeesamples.bll.CustomerService;
import com.javaeesamples.exceptions.EntityNotFoundException;
import com.javaeesamples.model.Customer;

public class CustomerServiceTests {

	private static ClassPathXmlApplicationContext context;
	private static CustomerService custService;

	@BeforeClass
	public static void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("jpaContext-Test.xml");
		context.registerShutdownHook();

		custService = context.getBean(CustomerService.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		context.close();
	}

	@Test
	public void can_save_and_retrieve_customer() {
		assertThat(custService, notNullValue());

		Customer customer = new Customer();
		customer.setContactName("Joe Root");
		customer.setPhoneNumber("199-100-1000");
		customer.setAddress("123 Market Street");
		customer.setCity("San Francisco");
		customer.setState("CA");
		customer.setPostalCode("94123");
		customer.setCountry("US");

		customer = custService.save(customer);

		Customer rCustomer = null;
		try {
			rCustomer = custService.get(customer.getId());
		} catch (EntityNotFoundException e) {
			fail("customer should have been present");
		}

		assertThat(rCustomer, notNullValue());
		assertThat(rCustomer.getContactName(), is("Joe Root"));
		assertThat(rCustomer.getPhoneNumber(), is("199-100-1000"));
		assertThat(rCustomer.getAddress(), is("123 Market Street"));
		assertThat(rCustomer.getCity(), is("San Francisco"));
		assertThat(rCustomer.getState(), is("CA"));
		assertThat(rCustomer.getPostalCode(), is("94123"));
		assertThat(rCustomer.getCountry(), is("US"));

		try {
			custService.delete(rCustomer.getId());
		} catch (EntityNotFoundException e1) {
			fail("customer should have been deleted");
		}

		try {
			rCustomer = custService.get(customer.getId());
		} catch (EntityNotFoundException e) {
			assertThat(e, notNullValue());
		}

	}

	@Test
	public void can_delete_employee() {
		assertThat(custService, notNullValue());

		Customer customer = new Customer();
		customer.setContactName("Joe Root");
		customer.setPhoneNumber("199-100-1000");
		customer.setAddress("123 Market Street");
		customer.setCity("San Francisco");
		customer.setState("CA");
		customer.setPostalCode("94123");
		customer.setCountry("US");

		customer = custService.save(customer);

		Customer rCustomer = null;
		try {
			rCustomer = custService.get(customer.getId());
		} catch (EntityNotFoundException e) {
			fail("customer should have been present");
		}

		assertThat(rCustomer, notNullValue());

		try {
			custService.delete(rCustomer.getId());
		} catch (EntityNotFoundException e) {
			fail("customer should have been deleted");
		}

		try {
			rCustomer = custService.get(rCustomer.getId());
		} catch (EntityNotFoundException e) {
			assertThat(e, notNullValue());
		}
	}

	@Test
	public void can_update_employee() {
		assertThat(custService, notNullValue());

		Customer customer = new Customer();
		customer.setContactName("Joe Root");
		customer.setPhoneNumber("199-100-1000");
		customer.setAddress("123 Market Street");
		customer.setCity("San Francisco");
		customer.setState("CA");
		customer.setPostalCode("94123");
		customer.setCountry("US");

		customer = custService.save(customer);

		Customer rCustomer = null;
		try {
			rCustomer = custService.get(customer.getId());
		} catch (EntityNotFoundException e) {
			fail("customer should have been present");
		}
		
		assertThat(rCustomer, notNullValue());
		assertThat(rCustomer.getContactName(), is("Joe Root"));
		assertThat(rCustomer.getPhoneNumber(), is("199-100-1000"));
		assertThat(rCustomer.getAddress(), is("123 Market Street"));
		assertThat(rCustomer.getCity(), is("San Francisco"));
		assertThat(rCustomer.getState(), is("CA"));
		assertThat(rCustomer.getPostalCode(), is("94123"));
		assertThat(rCustomer.getCountry(), is("US"));

		rCustomer.setContactName("Ian");
		rCustomer.setPhoneNumber("199-100-1001");
		rCustomer.setAddress("124 Market Street");
		rCustomer.setCity("Virginia");
		rCustomer.setState("VA");
		rCustomer.setPostalCode("94133");

		rCustomer = custService.save(rCustomer);
		assertThat(rCustomer, notNullValue());
		assertThat(rCustomer.getContactName(), is("Ian"));
		assertThat(rCustomer.getPhoneNumber(), is("199-100-1001"));
		assertThat(rCustomer.getAddress(), is("124 Market Street"));
		assertThat(rCustomer.getCity(), is("Virginia"));
		assertThat(rCustomer.getState(), is("VA"));
		assertThat(rCustomer.getPostalCode(), is("94133"));
		assertThat(rCustomer.getCountry(), is("US"));

		try {
			custService.delete(rCustomer.getId());	
		} catch (EntityNotFoundException e) {
			fail("customer should have been deleted");
		}
		
		try {
			rCustomer = custService.get(customer.getId());
		} catch (EntityNotFoundException e) {
			assertThat(e, notNullValue());
		}
	}

	@Test
	@Category(PagingCategory.class)
	public void can_page_allemployees() {
		assertThat(custService, notNullValue());

		Customer customer = new Customer();
		customer.setContactName("Ian");
		customer.setPhoneNumber("199-100-1000");
		customer.setAddress("123 Market Street");
		customer.setCity("San Francisco");
		customer.setState("CA");
		customer.setPostalCode("94123");
		customer.setCountry("US");

		customer = custService.save(customer);

		Customer customer2 = new Customer();
		customer2.setContactName("Ian1");
		customer2.setPhoneNumber("199-100-1001");
		customer2.setAddress("123 Market Street");
		customer2.setCity("San Francisco");
		customer2.setState("CA");
		customer2.setPostalCode("94123");
		customer2.setCountry("US");

		customer2 = custService.save(customer2);

		List<Customer> customers = custService.getAll(new PageRequest(0, 1,
				Direction.ASC, new String[] { "ContactName" }));
		assertThat(customers, notNullValue());
		assertThat(customers.size(), is(1));
		assertThat(customers.get(0).getContactName(), is("Ian"));

		customers = custService.getAll(new PageRequest(1, 1, Direction.ASC,
				new String[] { "ContactName" }));
		assertThat(customers, notNullValue());
		assertThat(customers.size(), is(1));
		assertThat(customers.get(0).getContactName(), is("Ian1"));

		customers = custService.getAll(new PageRequest(0, 2));
		assertThat(customers, notNullValue());
		assertThat(customers.size(), is(2));
		assertThat(customers.get(0).getContactName(), is("Ian"));

		try {
			custService.delete(customer.getId());
			custService.delete(customer2.getId());
		} catch (EntityNotFoundException e) {
		}

		customers = custService.getAll(new PageRequest(0, 2));
		assertThat(customers, notNullValue());
		assertThat(customers.size(), is(0));
	}
}
