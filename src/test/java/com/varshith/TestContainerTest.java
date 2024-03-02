package com.varshith;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class TestContainerTest {

  @Container
  private static final  PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER =
      new PostgreSQLContainer<>()
          .withDatabaseName("uint-test-database")
          .withUsername("varshithgowdav")
          .withPassword("Leela@1422");

  @Test
  void canStartPostgresDb() {
    assertThat(POSTGRE_SQL_CONTAINER.isRunning()).isTrue();
    assertThat(POSTGRE_SQL_CONTAINER.isCreated()).isTrue();
  }
}
