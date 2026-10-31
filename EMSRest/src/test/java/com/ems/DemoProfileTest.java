package com.ems;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.ems.repository.EmployeeRepository;

/** The "demo" profile boots on H2 with sample data and serves the OpenAPI docs. */
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:emsdemo-test;MODE=MySQL;DB_CLOSE_DELAY=-1")
@AutoConfigureMockMvc
@ActiveProfiles("demo")
class DemoProfileTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private EmployeeRepository employeeRepository;

    @Test
    void seedsSampleEmployeesWithoutExposingPasswords() throws Exception {
        assertThat(employeeRepository.count()).isEqualTo(6);
        mockMvc.perform(get("/api/ems/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$[0].password").doesNotExist());
    }

    @Test
    void servesOpenApiDocsAndRedirectsRootToSwagger() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("EMSRest: Employee Management REST API"));
        mockMvc.perform(get("/")).andExpect(redirectedUrl("/swagger-ui.html"));
    }
}
