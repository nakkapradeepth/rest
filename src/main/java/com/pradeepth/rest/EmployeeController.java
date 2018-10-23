package com.pradeepth.rest;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Documentation
 *
 * @author sunild
 * @since 1;
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

	DatabaseUtil databaseUtil = new DatabaseUtil();
	int id = 1000;

	@PostMapping
	public void createEmployee(@RequestBody Employee emp) {

		if (databaseUtil.getConnection() == null) {
			databaseUtil.initialize();
		}

		databaseUtil.createEmployeeTable();
		emp.setId(id++);
		databaseUtil.createEmployee(emp);
	}

	@GetMapping
	public List<Employee> getEmployees() {
		if (databaseUtil.getConnection() == null) {
			databaseUtil.initialize();
		}
		return databaseUtil.getAllEmployees();
	}

	@GetMapping("/{id}")
	public Employee getEmployee(@PathVariable("id") int id) {
		if (databaseUtil.getConnection() == null) {
			databaseUtil.initialize();
		}
		return databaseUtil.getEmployee(id);
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable("id") int id) {
		if (databaseUtil.getConnection() == null) {
			databaseUtil.initialize();
		}
		Employee employee = databaseUtil.getEmployee(id);
		if (employee != null) {
			databaseUtil.deleteEmployee(id);
		}
	}
}
