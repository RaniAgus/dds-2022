package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ContextTest implements PersistenceTest {

  @Test
  public void contextUp() {
    assertNotNull(entityManager());
  }

  @Test
  public void contextUpWithTransaction() {
    withTransaction(() -> {});
  }

}
