package com.javaeesamples.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaeesamples.exceptions.EntityNotFoundException;
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
	public Employee get(long id) throws EntityNotFoundException {
		Employee employee = employeeRepo.findOne(id);
		if(employee == null)
			throw new EntityNotFoundException();
		
		return employee;
	}

	@Override
	@Transactional
	public void delete(long id) throws EntityNotFoundException {
		Employee employee = employeeRepo.findOne(id);
		if(employee == null)
			throw new EntityNotFoundException();
		employeeRepo.delete(employee);
	}

	@Override
	public List<Employee> getAll(Pageable pageRequest) {
		return employeeRepo.findAll(pageRequest).getContent();
	}
}
