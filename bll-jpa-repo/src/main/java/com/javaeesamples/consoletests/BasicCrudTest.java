package com.javaeesamples.consoletests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.javaeesamples.bll.EmployeeService;
import com.javaeesamples.model.Employee;

@Component
public class BasicCrudTest {

	@Autowired
	public EmployeeService serv;

	public ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ApplicationContextLoader loader = new ApplicationContextLoader();
		loader.load(null, "jpaContext-Test.xml");

		BasicCrudTest main = loader.applicationContext.getBean(BasicCrudTest.class);
        
		Employee emp = new Employee();
		emp.setFirstName("Prasad");
		emp.setLastName("Mangat");
		emp.setTitle("Engineer");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date d = sdf.parse("17/12/2005");
			emp.setDateOfBirth(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		main.serv.save(emp);
	}
}
