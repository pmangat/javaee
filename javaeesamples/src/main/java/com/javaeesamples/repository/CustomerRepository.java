package com.javaeesamples.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaeesamples.model.Customer;

@Repository("customerRepository")
public interface CustomerRepository extends
		JpaRepository<Customer, Serializable> {
}
