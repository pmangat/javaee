package com.javaeesamples.bll;

import com.javaeesamples.exceptions.EntityNotFoundException;
import com.javaeesamples.model.Orders;

public interface OrdersService {
	Orders save(Orders order);
	Orders get(long id) throws EntityNotFoundException;
	void delete(long id) throws EntityNotFoundException;
}