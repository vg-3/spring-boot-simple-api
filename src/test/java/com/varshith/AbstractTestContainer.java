package com.varshith;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractTestContainer {

  @BeforeAll
 static void beforeAll() {
    Flyway flyway = Flyway.configure().dataSource(
        POSTGRE_SQL_CONTAINER.getJdbcUrl(),
        POSTGRE_SQL_CONTAINER.getUsername(),
        POSTGRE_SQL_CONTAINER.getPassword()
    ).load();
    flyway.migrate();
  }
  @Container
  protected static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER =
      new PostgreSQLContainer<>()
          .withDatabaseName("uint-test-database")
          .withUsername("varshithgowdav")
          .withPassword("Leela@1422");

  @DynamicPropertySource
  private static void registerDataSourceProperty(DynamicPropertyRegistry registry){
    registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
  }
}
