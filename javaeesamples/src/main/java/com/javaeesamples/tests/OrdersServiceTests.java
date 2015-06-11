package com.javaeesamples.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.javaeesamples.bll.OrdersService;
import com.javaeesamples.model.Customer;
import com.javaeesamples.model.Employee;
import com.javaeesamples.model.OrderDetail;
import com.javaeesamples.model.Orders;

public class OrdersServiceTests {
	private static ClassPathXmlApplicationContext context;
	private static OrdersService ordersService;

	@BeforeClass
	public static void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("jpaContext-Test.xml");
		context.registerShutdownHook();

		ordersService = context.getBean(OrdersService.class);
	}

	@AfterClass
	public static void tearDown() {
		context.close();
	}

	@Test
	public void can_save_and_retrieve_orders() {
		assertThat(ordersService, notNullValue());

		Orders order = new Orders();
		Customer customer = CreateCustomer();
		Employee employee = CreateEmployee();

		order.setCustomer(customer);
		order.setEmployee(employee);
		order.setFreight(10.0);
		order.setOrderDate(Utils.getDate("01/01/2015", "MM/dd/yyy"));
		order.setShipDate(Utils.getDate("01/02/2015", "MM/dd/yyyy"));

		Set<OrderDetail> details = new HashSet<OrderDetail>();

		OrderDetail detail = new OrderDetail();
		detail.setProductName("Baby wipes");
		detail.setDiscount(0.0);
		detail.setQuantity(2);
		detail.setUnitPrice(3.5);
		details.add(detail);

		detail = new OrderDetail();
		detail.setProductName("Baby milk powder");
		detail.setDiscount(3.0);
		detail.setQuantity(2);
		detail.setUnitPrice(20.00);
		details.add(detail);

		order.setOrderDetails(details);

		Orders sOrder = ordersService.save(order);

		Orders rOrder = ordersService.get(sOrder.getId());
		assertThat(rOrder, notNullValue());
		assertThat(rOrder.getCustomer().getContactName(), is("Ian"));
		assertThat(rOrder.getEmployee().getLastName(), is("Root"));
		assertThat(rOrder.getOrderDetails().size(), is(2));
		assertThat(
				rOrder.getOrderDetails().stream()
						.filter(a -> a.getProductName() == "Baby wipes")
						.count(), is(1L));
		assertThat(
				rOrder.getOrderDetails().stream()
						.filter(a -> a.getProductName() == "Baby milk powder")
						.count(), is(1L));
	}

	private Customer CreateCustomer() {
		Customer customer = new Customer();
		customer.setContactName("Ian");
		customer.setPhoneNumber("199-100-1000");
		customer.setAddress("123 Market Street");
		customer.setCity("San Francisco");
		customer.setState("CA");
		customer.setPostalCode("94123");
		customer.setCountry("US");

		return customer;
	}

	private Employee CreateEmployee() {
		Employee emp = new Employee();
		emp.setFirstName("Joe");
		emp.setLastName("Root");
		emp.setTitle("Engineer");
		Date dt = Utils.getDate("12/12/2005", "MM/dd/yyyy");
		emp.setDateOfBirth(dt);

		return emp;
	}
}
