package com.javaeesamples.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaeesamples.model.Orders;

@Repository("ordersRepository")
public interface OrdersRepository extends JpaRepository<Orders, Serializable> {
}
