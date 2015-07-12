package com.javaeesamples.bll;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.javaeesamples.exceptions.EntityNotFoundException;
import com.javaeesamples.model.Employee;

public interface EmployeeService {
	Employee save(Employee employee);
	Employee get(long id) throws EntityNotFoundException;
	List<Employee> getAll(Pageable pageRequest);
	void delete(long id) throws EntityNotFoundException;
}
