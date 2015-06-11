package com.javaeesamples.bll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaeesamples.model.Orders;
import com.javaeesamples.repository.OrdersRepository;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersRepository ordersRepo;

	@Override
	@Transactional
	public Orders save(Orders order) {
		return ordersRepo.save(order);
	}

	@Override
	public Orders get(long id) {
		return ordersRepo.findOne(id);
	}

	@Override
	@Transactional
	public void delete(long id) {
		Orders order = ordersRepo.findOne(id);
		ordersRepo.delete(order);
	}
}
