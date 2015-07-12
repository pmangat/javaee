package com.springsample.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaeesamples.bll.CustomerService;
import com.javaeesamples.exceptions.EntityNotFoundException;
import com.javaeesamples.model.Customer;
import com.javaeesamples.model.Orders;
import com.javaeesamples.model.SortDirection;
import com.springsample.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	CustomerService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Customer getCustomer(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		Customer customer;
		try {
			customer = service.get(id);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException();
		}

		return customer;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Customer> getEmployees(
			@RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "direction", required = false, defaultValue = "ASC") SortDirection direction,
			@RequestParam(value = "sortColumns", required = false) List<String> sortColumns) {
		PageRequest pageRequest = constructPageRequest(pageIndex, pageSize,
				direction, sortColumns);
		return service.getAll(pageRequest);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Customer addCustomer(@RequestBody Customer customer) {
		return service.save(customer);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteCustomer(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		try {
			service.delete(id);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException();
		}
	}

	@RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
	public List<Orders> getOrders(@PathVariable(value = "id") Long id) {
		return service.getOrders(id);
	}

	private PageRequest constructPageRequest(int pageIndex, int pageSize,
			SortDirection direction, List<String> sortColumns) {

		if (sortColumns == null)
			sortColumns = new ArrayList<String>();

		if (sortColumns.size() == 0)
			sortColumns.add("ContactName");

		return new PageRequest(pageIndex, pageSize,
				Direction.fromString(direction.toString()),
				sortColumns.toArray(new String[sortColumns.size()]));
	}

}