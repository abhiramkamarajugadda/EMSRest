package com.ems.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.model.Employee;
import com.ems.service.EmployeeService;

@RestController
@RequestMapping("/api/ems/employees")
public class EmployeeController {
	private EmployeeService service;

	public EmployeeController(EmployeeService service) {
		super();
		this.service = service;
	}
	@PostMapping
	public ResponseEntity<?> saveEmployee(@RequestBody Employee employee){
		Employee obj = service.saveEmployee(employee);
		if(obj != null) {
			return new ResponseEntity<>("Employee added successfully....",HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("Error in adding employee....",HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping
	public ResponseEntity<?> getAllEmployees(){
		List<Employee> empList = service.getAllEmployees();
		if(empList.size()==0) {
			return new ResponseEntity<>("Sorry not found ",HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(empList,HttpStatus.FOUND);
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> searchEmployee(@PathVariable Integer id){
		Employee employee = service.searchEmployee(id);
		if(employee.getId()!=null) {
			return new ResponseEntity<>(employee,HttpStatus.FOUND);
		}else {
			return new ResponseEntity<>("invalid id .........",HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee){
		Employee obj = service.searchEmployee(employee.getId());
		if(obj.getId()!=null) {
			service.saveEmployee(employee);
			return new ResponseEntity<>("Employee updated......",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Invalid id.......",HttpStatus.BAD_REQUEST);
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Integer id){
		Employee employee = service.searchEmployee(id);
		if(employee.getId()!=null) {
			service.deleteEmployee(id);
			return new ResponseEntity<>("Employee Deleted successfully.....",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("invalid id .........",HttpStatus.BAD_REQUEST);
		}
	}
}
