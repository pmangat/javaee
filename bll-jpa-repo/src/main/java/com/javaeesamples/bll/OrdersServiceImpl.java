package com.javaeesamples.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaeesamples.exceptions.EntityNotFoundException;
import com.javaeesamples.model.Customer;
import com.javaeesamples.model.Employee;
import com.javaeesamples.model.Orders;
import com.javaeesamples.repository.CustomerRepository;
import com.javaeesamples.repository.EmployeeRepository;
import com.javaeesamples.repository.OrdersRepository;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersRepository ordersRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	@Transactional
	public Orders save(Orders order) {

		if (order.getCustomer() == null) {
			Customer customer = customerRepo.findOne(order.getCustomerId());
			order.setCustomer(customer);
		}

		if (order.getEmployee() == null) {
			Employee employee = employeeRepo.findOne(order.getEmployeeId());
			order.setEmployee(employee);
		}

		return ordersRepo.save(order);
	}

	@Override
	public Orders get(long id) throws EntityNotFoundException {
		Orders order = ordersRepo.findOne(id);
		if(order == null)
			throw new EntityNotFoundException();
		return order;
	}

	@Override
	@Transactional
	public void delete(long id) throws EntityNotFoundException {
		Orders order = ordersRepo.findOne(id);
		if(order == null)
			throw new EntityNotFoundException();
		ordersRepo.delete(order);
	}
}
