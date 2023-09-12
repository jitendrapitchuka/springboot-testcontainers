package com.jitendra.springcontainers;

import com.jitendra.springcontainers.entity.Student;
import com.jitendra.springcontainers.repo.StudentRepo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class SpringbootTestContainersApplicationTests {
	@Container
	private static PostgreSQLContainer  postgreSQLContainer=new PostgreSQLContainer("postgres:15-alpine");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}

	@BeforeAll
	static void beforeAll() {
		postgreSQLContainer.start();
	}

	@AfterAll
	static void afterAll() {
		postgreSQLContainer.stop();
	}
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private StudentRepo studentRepo;
	@Test
	public void allStudents() throws Exception {
	//given-setup

	System.out.println(postgreSQLContainer.getJdbcUrl());

		List<Student>students= List.of(Student.builder().name("jithubhai").email("farziemail@emailcom").build(),
				Student.builder().name("balu").email("dupbalu@emai.com").build());

		studentRepo.saveAll(students);
		// when-action

		ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/api/students"));


		// then	-verify

		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.size()",is(2)));
	}
}
