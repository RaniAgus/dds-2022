package got;

import org.junit.jupiter.api.Test;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import static org.junit.jupiter.api.Assertions.*;

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

  @Test
  public void contextUp() {
    assertNotNull(entityManager());
  }
}
