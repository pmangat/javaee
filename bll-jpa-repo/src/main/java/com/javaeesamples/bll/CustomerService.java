package com.javaeesamples.bll;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.javaeesamples.model.Customer;
import com.javaeesamples.model.Orders;

public interface CustomerService {
	Customer save(Customer customer);
	Customer get(long id);
	List<Customer> getAll(Pageable pageRequest);
	void delete(long id);

	List<Orders> getOrders(long id);
}