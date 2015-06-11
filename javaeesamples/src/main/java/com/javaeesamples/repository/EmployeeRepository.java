package com.javaeesamples.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javaeesamples.model.Employee;

@Repository("employeeRepository")
public interface EmployeeRepository extends
		JpaRepository<Employee, Serializable> {
}
