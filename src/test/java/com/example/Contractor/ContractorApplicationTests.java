package com.example.Contractor;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {ContractorApplicationTests.Initializer.class})
@TestPropertySource(locations = "classpath:application-test.properties")
class ContractorApplicationTests {

	@Container
	public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
			.withReuse(true)
			.withDatabaseName("testDatabase")
			.withUsername("testUser")
			.withPassword("testPass");

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext context) {
			TestPropertyValues.of(
					"spring.datasource.username=" + container.getUsername(),
					"spring.datasource.password=" + container.getPassword(),
					"spring.datasource.url=" + container.getJdbcUrl()
			).applyTo(context.getEnvironment());
		}
	}

}
