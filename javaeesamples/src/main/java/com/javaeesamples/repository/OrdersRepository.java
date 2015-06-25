package com.javaeesamples.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaeesamples.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Serializable> {
}
