package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.model.Employee;
import com.model.Employees;

@SpringBootTest(classes = RestfulTestingApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class RestfulTestingApplicationTests {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Sql({ "classpath:schema.sql", "classpath:data.sql" })
	
	@Test
	public void testAllEmployees() 
	{
		assertTrue(
				this.restTemplate
					.getForObject("http://localhost:" + port + "/employees", Employees.class)
					.getEmployeeList().size() == 3);
	}
	
	@Test
	public void testAddEmployee() {
		Employee employee = new Employee("Chaman", "Bharti", "chaman.bharti@gmail.com");
		ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://localhost:"+port+"/employees", employee, String.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
	}
}
