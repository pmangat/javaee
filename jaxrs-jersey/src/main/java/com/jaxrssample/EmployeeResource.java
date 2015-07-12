package com.jaxrssample;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.javaeesamples.bll.EmployeeService;
import com.javaeesamples.exceptions.EntityNotFoundException;
import com.javaeesamples.model.Employee;
import com.javaeesamples.model.SortDirection;

@Path("employees")
public class EmployeeResource {
	@Autowired
	private EmployeeService service;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	public Employee addEmployee(Employee employee) {
		Employee emp = service.save(employee);
		return emp;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteEmployee(@PathParam(value = "id") Long id) {
		try {
			service.delete(id);
		} catch (EntityNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Employee not found").build();
		}
		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getEmployee(@PathParam(value = "id") Long id) {
		Employee emp;
		try {
			emp = service.get(id);
		} catch (EntityNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Employee not found").build();
		}
		return Response.ok(emp, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("")
	public List<Employee> getEmployees(
			@QueryParam(value = "pageSize") int pageSize,
			@QueryParam(value = "pageIndex") int pageIndex,
			@QueryParam(value = "direction") SortDirection direction,
			@QueryParam(value = "sortColumns") List<String> sortColumns) {

		PageRequest pageRequest = constructPageRequest(pageIndex, pageSize,
				direction, sortColumns);
		return service.getAll(pageRequest);
	}

	private PageRequest constructPageRequest(int pageIndex, int pageSize,
			SortDirection direction, List<String> sortColumns) {

		if (pageIndex < 0)
			pageIndex = 0;
		if (pageSize <= 0)
			pageSize = 50;
		if (direction == null)
			direction = SortDirection.ASC;
		if (sortColumns == null)
			sortColumns = new ArrayList<String>();

		if (sortColumns.size() == 0)
			sortColumns.add("LastName");

		return new PageRequest(pageIndex, pageSize,
				Direction.fromString(direction.toString()),
				sortColumns.toArray(new String[sortColumns.size()]));
	}

}
