package com.javaeesamples.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaeesamples.model.Employee;
import com.javaeesamples.repository.EmployeeRepository;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	@Transactional
	public Employee save(Employee employee) {
		return employeeRepo.save(employee);
	}

	@Override
	public Employee get(long id) {
		return employeeRepo.findOne(id);
	}

	@Override
	@Transactional
	public void delete(long id) {
		Employee employee = employeeRepo.findOne(id);
		employeeRepo.delete(employee);
	}

	@Override
	public List<Employee> getAll(Pageable pageRequest) {
		return employeeRepo.findAll(pageRequest).getContent();
	}
}
