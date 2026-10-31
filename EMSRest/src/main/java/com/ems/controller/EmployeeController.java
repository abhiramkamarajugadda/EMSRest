package com.ems.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.model.Employee;
import com.ems.service.EmployeeService;

/**
 * Employee REST API.
 * 200 OK for reads and updates, 201 Created (with a Location header) for creates,
 * 204 No Content for deletes, 404 Not Found for unknown ids and 400 for a missing id on update.
 */
@RestController
@RequestMapping("/api/ems/employees")
public class EmployeeController {
	private final EmployeeService service;

	public EmployeeController(EmployeeService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		employee.setId(null);
		Employee saved = service.saveEmployee(employee);
		return ResponseEntity.created(URI.create("/api/ems/employees/" + saved.getId())).body(saved);
	}

	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return ResponseEntity.ok(service.getAllEmployees());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> searchEmployee(@PathVariable Integer id) {
		return service.searchEmployee(id)
				.<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElseGet(() -> notFound(id));
	}

	@PutMapping
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
		if (employee.getId() == null) {
			return ResponseEntity.badRequest().body("The employee id is required for an update.");
		}
		if (!service.exists(employee.getId())) {
			return notFound(employee.getId());
		}
		return ResponseEntity.ok(service.saveEmployee(employee));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
		if (!service.exists(id)) {
			return notFound(id);
		}
		service.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}

	private ResponseEntity<?> notFound(Integer id) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with id " + id + " was not found.");
	}
}
