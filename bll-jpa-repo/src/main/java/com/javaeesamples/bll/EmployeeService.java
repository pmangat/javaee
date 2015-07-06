package com.javaeesamples.bll;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.javaeesamples.model.Employee;

public interface EmployeeService {
	Employee save(Employee employee);
	Employee get(long id);
	List<Employee> getAll(Pageable pageRequest);
	void delete(long id);
}
