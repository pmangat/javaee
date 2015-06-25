package com.javaeesamples.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaeesamples.model.Employee;

public interface EmployeeRepository extends
		JpaRepository<Employee, Serializable> {
}
