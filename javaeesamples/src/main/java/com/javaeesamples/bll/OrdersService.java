package com.javaeesamples.bll;

import com.javaeesamples.model.Orders;

public interface OrdersService {
	Orders save(Orders order);
	Orders get(long id);
	void delete(long id);
}