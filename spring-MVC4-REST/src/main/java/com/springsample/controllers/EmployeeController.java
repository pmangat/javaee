package com.springsample.controllers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaeesamples.bll.EmployeeService;
import com.javaeesamples.model.Employee;
import com.javaeesamples.model.SortDirection;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Employee getEmployee(@PathVariable(value = "id") String id) {
		Employee emp = service.get(Long.parseLong(id));
		return emp;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Employee> getEmployees(@RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(value = "direction", required = false, defaultValue = "ASC") SortDirection direction,
			@RequestParam(value = "sortColumns", required = false) List<String> sortColumns)
	{
		PageRequest pageRequest = constructPageRequest(pageIndex, pageSize,
				direction, sortColumns);
		return service.getAll(pageRequest);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Employee addEmployee(@RequestBody Employee employee) {
		Employee emp = service.save(employee);
		return emp;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteEmployee(@PathVariable(value = "id") String id) {
		service.delete(Long.parseLong(id));
	}
	
	private PageRequest constructPageRequest(int pageIndex, int pageSize,
			SortDirection direction, List<String> sortColumns) {

		if (sortColumns == null)
			sortColumns = new ArrayList<String>();
		
		if(sortColumns.size() == 0)
			sortColumns.add("LastName");

		return new PageRequest(pageIndex, pageSize,
				Direction.fromString(direction.toString()),
				sortColumns.toArray(new String[sortColumns.size()]));
	}

}
