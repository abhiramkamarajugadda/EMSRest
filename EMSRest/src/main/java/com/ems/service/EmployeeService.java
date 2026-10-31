package com.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;

@Service
public class EmployeeService {
	private EmployeeRepository repository; 
	public EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}
	public Employee saveEmployee(Employee employee) {
		return repository.save(employee);
	}
	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}
	public Optional<Employee> searchEmployee(Integer id) {
		return id == null ? Optional.empty() : repository.findById(id);
	}

	public boolean exists(Integer id) {
		return id != null && repository.existsById(id);
	}
	public void deleteEmployee(Integer id) {
		repository.deleteById(id);
	}

}
