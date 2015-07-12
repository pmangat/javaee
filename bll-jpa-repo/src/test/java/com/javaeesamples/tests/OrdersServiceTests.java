package com.javaeesamples.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.javaeesamples.bll.CustomerService;
import com.javaeesamples.bll.EmployeeService;
import com.javaeesamples.bll.OrdersService;
import com.javaeesamples.exceptions.EntityNotFoundException;
import com.javaeesamples.model.Customer;
import com.javaeesamples.model.Employee;
import com.javaeesamples.model.OrderDetail;
import com.javaeesamples.model.Orders;

public class OrdersServiceTests {
	private static ClassPathXmlApplicationContext context;
	private static OrdersService ordersService;
	private static CustomerService customerService;
	private static EmployeeService employeeService;

	@BeforeClass
	public static void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("jpaContext-Test.xml");
		context.registerShutdownHook();

		ordersService = context.getBean(OrdersService.class);
		customerService = context.getBean(CustomerService.class);
		employeeService = context.getBean(EmployeeService.class);
	}

	@AfterClass
	public static void tearDown() {
		context.close();
	}

	@Test
	public void can_save_and_retrieve_orders() {
		assertThat(ordersService, notNullValue());
		
		Customer customer = createCustomer();
		Employee employee = createEmployee();
		
		Orders order = createOrder();
		order.setCustomerId(customer.getId());
		order.setEmployeeId(employee.getId());
		
		Set<OrderDetail> details = new HashSet<OrderDetail>();
		OrderDetail detail = createOrderDetail("Baby wipes", 0.0, 2, 3.5);
		details.add(detail);
		detail = createOrderDetail("Baby milk powder", 3.0, 2, 20.00);
		details.add(detail);

		order.setOrderDetails(details);

		assertThat(customer, notNullValue());
		assertThat(employee, notNullValue());
		assertThat(order, notNullValue());
		
		Orders sOrder = ordersService.save(order);

		Orders rOrder = null;
		try {
			rOrder = ordersService.get(sOrder.getId());
		} catch (EntityNotFoundException e) {
			fail("Order should have been found");
		}
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

	@Test
	public void can_delete_orders() {
		assertThat(ordersService, notNullValue());
		Customer customer = createCustomer();
		Employee employee = createEmployee();
		
		Orders order = createOrder();
		order.setCustomerId(customer.getId());
		order.setEmployeeId(employee.getId());

		Set<OrderDetail> details = new HashSet<OrderDetail>();
		OrderDetail detail = createOrderDetail("Baby wipes", 0.0, 2, 3.5);
		details.add(detail);
		detail = createOrderDetail("Baby milk powder", 3.0, 2, 20.00);
		details.add(detail);

		order.setOrderDetails(details);

		Orders sOrder = ordersService.save(order);

		Orders rOrder = null;
		try {
			rOrder = ordersService.get(sOrder.getId());
		} catch (EntityNotFoundException e) {
			fail("Order should have been found");
		}
		assertThat(rOrder, notNullValue());
		assertThat(rOrder.getOrderDetails().size(), is(2));

		try {
			ordersService.delete(rOrder.getId());
		} catch (EntityNotFoundException e) {
			fail("order should have been deleted");
		}
		
		try {
			rOrder = ordersService.get(rOrder.getId());
		} catch (EntityNotFoundException e) {
			assertThat(e, notNullValue());
		}
		
	}

	@Test
	public void can_update_orders() {
		assertThat(ordersService, notNullValue());
		
		Customer customer = createCustomer();
		Employee employee = createEmployee();
		
		Orders order = createOrder();
		order.setCustomerId(customer.getId());
		order.setEmployeeId(employee.getId());

		Set<OrderDetail> details = new HashSet<OrderDetail>();
		OrderDetail detail = createOrderDetail("Baby wipes", 0.0, 2, 3.5);
		details.add(detail);
		detail = createOrderDetail("Baby milk powder", 3.0, 2, 20.00);
		details.add(detail);

		order.setOrderDetails(details);

		Orders sOrder = ordersService.save(order);

		Orders rOrder = null;
		try {
			rOrder = ordersService.get(sOrder.getId());
		} catch (EntityNotFoundException e) {
			fail("order should have been found");
		}
		assertThat(rOrder, notNullValue());
		assertThat(rOrder.getOrderDetails().size(), is(2));

		rOrder.setCustomerId(customer.getId());
		rOrder.setEmployeeId(employee.getId());
		Set<OrderDetail> rdetails = rOrder.getOrderDetails();
		OrderDetail lineItem1 = rdetails.stream().filter(a -> a.getProductName() == "Baby wipes").findFirst().get();
		lineItem1.setQuantity(5);
		OrderDetail lineItem2 = rdetails.stream().filter(a -> a.getProductName() == "Baby milk powder").findFirst().get();
		lineItem2.setUnitPrice(21.00);
		
		sOrder = ordersService.save(rOrder);
		
		try {
			rOrder = ordersService.get(sOrder.getId());
		} catch (EntityNotFoundException e) {
			fail("order should have been found");
		}
		assertThat(rOrder, notNullValue());
		assertThat(rOrder.getOrderDetails().size(), is(2));
		
		rdetails = rOrder.getOrderDetails();
		lineItem1 = rdetails.stream().filter(a -> a.getProductName() == "Baby wipes").findFirst().get();
		assertThat(lineItem1.getQuantity(), is(5));
		lineItem2 = rdetails.stream().filter(a -> a.getProductName() == "Baby milk powder").findFirst().get();
		assertThat(lineItem2.getUnitPrice(), is(21.00));
		
	}
	
	@Test
	public void can_retrieve_orders_for_customer()
	{
		assertThat(ordersService, notNullValue());
		assertThat(customerService, notNullValue());
		Customer customer = createCustomer();
		Employee employee = createEmployee();
		
		Orders order = createOrder();
		order.setCustomerId(customer.getId());
		order.setEmployeeId(employee.getId());

		Set<OrderDetail> details = new HashSet<OrderDetail>();
		OrderDetail detail = createOrderDetail("Baby wipes", 0.0, 2, 3.5);
		details.add(detail);
		detail = createOrderDetail("Baby milk powder", 3.0, 2, 20.00);
		details.add(detail);

		order.setOrderDetails(details);
		
		ordersService.save(order);
		
		order = createOrder();
		order.setCustomerId(customer.getId());
		order.setEmployeeId(employee.getId());

		details = new HashSet<OrderDetail>();
		detail = createOrderDetail("Baby wipes", 0.0, 3, 3.5);
		details.add(detail);
		detail = createOrderDetail("Baby milk powder", 3.0, 4, 20.00);
		details.add(detail);

		order.setOrderDetails(details);
		
		ordersService.save(order);
		
		List<Orders> orders = customerService.getOrders(customer.getId());
		assertThat(orders, notNullValue());
		assertThat(orders.size(), is(2));
	}
	
	private OrderDetail createOrderDetail(String productName, double discount,
			int qty, double unitprice) {
		OrderDetail detail = new OrderDetail();
		detail.setProductName(productName);
		detail.setDiscount(discount);
		detail.setQuantity(qty);
		detail.setUnitPrice(unitprice);

		return detail;
	}

	private Orders createOrder() {
		Orders order = new Orders();
		order.setFreight(10.0);
		order.setOrderDate(Utils.getDate("01/01/2015", "MM/dd/yyy"));
		order.setShipDate(Utils.getDate("01/02/2015", "MM/dd/yyyy"));
		return order;
	}

	private Customer createCustomer() {
		Customer customer = new Customer();
		customer.setContactName("Ian");
		customer.setPhoneNumber("199-100-1000");
		customer.setAddress("123 Market Street");
		customer.setCity("San Francisco");
		customer.setState("CA");
		customer.setPostalCode("94123");
		customer.setCountry("US");

		return customerService.save(customer);
	}

	private Employee createEmployee() {
		Employee emp = new Employee();
		emp.setFirstName("Joe");
		emp.setLastName("Root");
		emp.setTitle("Engineer");
		Date dt = Utils.getDate("12/12/2005", "MM/dd/yyyy");
		emp.setDateOfBirth(dt);

		return employeeService.save(emp);
	}
}
