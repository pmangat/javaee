package com.javaeesamples.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaeesamples.model.Customer;

public interface CustomerRepository extends
			JpaRepository<Customer, Serializable> {
}
