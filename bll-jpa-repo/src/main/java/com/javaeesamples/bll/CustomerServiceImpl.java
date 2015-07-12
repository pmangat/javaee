package com.javaeesamples.bll;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaeesamples.exceptions.EntityNotFoundException;
import com.javaeesamples.model.Customer;
import com.javaeesamples.model.Orders;
import com.javaeesamples.repository.CustomerRepository;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Override
	@Transactional
	public Customer save(Customer customer) {
		return customerRepo.save(customer);
	}

	@Override
	public Customer get(long id) throws EntityNotFoundException {
		Customer customer = customerRepo.findOne(id);
		if(customer == null)
			throw new EntityNotFoundException();
		return customer;
	}

	@Override
	@Transactional
	public void delete(long id) throws EntityNotFoundException {
		Customer customer = customerRepo.findOne(id);
		if(customer == null)
			throw new EntityNotFoundException();
		customerRepo.delete(customer);
	}

	@Override
	public List<Customer> getAll(Pageable pageRequest) {
		return customerRepo.findAll(pageRequest).getContent();
	}

	@Override
	public List<Orders> getOrders(long id) {
		Customer customer = customerRepo.findOne(id);
		if (customer != null)
		{
			List<Orders> list = new ArrayList<Orders>(customer.getOrders());
			return list;
		}

		return new ArrayList<Orders>();
	}
}