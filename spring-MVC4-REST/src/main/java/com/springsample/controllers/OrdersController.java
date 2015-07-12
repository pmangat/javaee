package com.springsample.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javaeesamples.bll.CustomerService;
import com.javaeesamples.bll.EmployeeService;
import com.javaeesamples.bll.OrdersService;
import com.javaeesamples.exceptions.EntityNotFoundException;
import com.javaeesamples.model.Customer;
import com.javaeesamples.model.Employee;
import com.javaeesamples.model.Orders;
import com.springsample.exceptions.BadRequestException;
import com.springsample.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	OrdersService service;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Orders getOrder(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		Orders order;
		try {
			order = service.get(id);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException();
		}
		return order;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Orders addOrder(@RequestBody Orders order) throws BadRequestException {
		Customer customer;
		try {
			customer = customerService.get(order.getCustomerId());
		} catch (EntityNotFoundException e) {
			throw new BadRequestException("Invalid customer");
		}	
		
		Employee employee;
		try {
			employee = employeeService.get(order.getEmployeeId());
		} catch (EntityNotFoundException e) {
			throw new BadRequestException("Invalid employee");
		}
		
		order.setCustomer(customer);
		order.setEmployee(employee);
		
		Orders orders = service.save(order);
		return orders;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteOrder(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		try {
			service.get(id);
			service.delete(id);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException();
		}
	}
}
