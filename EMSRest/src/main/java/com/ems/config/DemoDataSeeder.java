package com.ems.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;

/** Seeds sample (fictional) employees when the "demo" profile is active. */
@Component
@Profile("demo")
public class DemoDataSeeder implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public DemoDataSeeder(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {
        if (employeeRepository.count() > 0) {
            return;
        }
        employeeRepository.saveAll(List.of(
                employee("Rahul Verma", "rahul.verma@example.com", 28, "Male", "9000000001", "Engineering", "Hyderabad"),
                employee("Ananya Gupta", "ananya.gupta@example.com", 31, "Female", "9000000002", "Human Resources", "Bengaluru"),
                employee("Kiran Patel", "kiran.patel@example.com", 26, "Male", "9000000003", "Finance", "Pune"),
                employee("Sneha Reddy", "sneha.reddy@example.com", 29, "Female", "9000000004", "Engineering", "Chennai"),
                employee("Arjun Das", "arjun.das@example.com", 35, "Male", "9000000005", "Sales", "Mumbai"),
                employee("Pooja Nair", "pooja.nair@example.com", 24, "Female", "9000000006", "Marketing", "Kochi")));
    }

    private static Employee employee(String name, String email, int age, String gender, String mobile,
                                     String department, String address) {
        // Placeholder password: the field is write-only and never returned by the API
        return new Employee(null, name, email, "demo-only", age, gender, mobile, department, address);
    }
}
