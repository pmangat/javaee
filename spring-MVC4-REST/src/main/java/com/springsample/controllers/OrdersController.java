package com.springsample.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javaeesamples.bll.OrdersService;
import com.javaeesamples.model.Orders;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	OrdersService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Orders getOrder(@PathVariable(value = "id") String id) {
		Orders order = service.get(Long.parseLong(id));
		return order;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Orders addOrder(@RequestBody Orders order) {
		return service.save(order);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteOrder(@PathVariable(value = "id") String id) {
		service.delete(Long.parseLong(id));
	}
}
