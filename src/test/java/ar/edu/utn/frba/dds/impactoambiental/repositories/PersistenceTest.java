package ar.edu.utn.frba.dds.impactoambiental.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public interface PersistenceTest extends TransactionalOps, EntityManagerOps, WithGlobalEntityManager {
  @BeforeEach
  default void setup() {
    beginTransaction();
  }

  @AfterEach
  default void teardown() {
    rollbackTransaction();
  }
}
