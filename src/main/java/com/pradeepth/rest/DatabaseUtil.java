package com.pradeepth.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Documentation
 *
 * @author Sunil Dabburi
 * @since 1
 */
public class DatabaseUtil {

	Connection connection;

	public void initialize() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ta", "root",
					"Admin1!Admin1!");
			ResultSet resultSet = connection.createStatement().executeQuery("select id, tenant_alias from company");
			while (resultSet.next()) {
				System.out.println(resultSet.getInt(1) + " : " + resultSet.getString(2));
			}
			resultSet.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void createEmployeeTable() {
		try {
			boolean status = connection.createStatement()
					.execute("create table employee_pradeepth(id INT(10), age INT(3), name VARCHAR(50))");
			System.out.println("Table created: " + status);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void createEmployee(Employee emp) {
		try {
			PreparedStatement statement = connection
					.prepareStatement("insert into employee_pradeepth values (?, ?, ?)");
			statement.setInt(1, emp.getId());
			statement.setInt(2, emp.getAge());
			statement.setString(3, emp.getName());
			boolean status = statement.execute();
			System.out.println("Employee created: " + status);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public Employee getEmployee(int id) {
		Employee employee = null;
		try {
			ResultSet resultSet = connection.createStatement()
					.executeQuery("select * from employee_pradeepth where id = " + id);
			while (resultSet.next()) {
				employee = new Employee();
				employee.setId(resultSet.getInt(1));
				employee.setAge(resultSet.getInt(2));
				employee.setName(resultSet.getString(3));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return employee;
	}

	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		try {
			ResultSet resultSet = connection.createStatement()
					.executeQuery("select id, age, name from employee_pradeepth");
			while (resultSet.next()) {
				Employee employee = new Employee();
				employee.setId(resultSet.getInt(1));
				employee.setAge(resultSet.getInt(2));
				employee.setName(resultSet.getString(3));
				employees.add(employee);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return employees;
	}

	public void deleteEmployee(int id) {
		try {
			PreparedStatement statement = connection
					.prepareStatement("delete from employee_pradeepth where id = ?");
			statement.setInt(1, id);
			boolean status = statement.execute();
			System.out.println("Employee deleted: " + status);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
