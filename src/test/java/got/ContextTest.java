package got;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

  @BeforeEach
  public void setup() {
    super.setup();
  }

  @AfterEach
  public void teardown() {
    super.tearDown();
  }

  @Test
  public void contextUp() {
    assertNotNull(entityManager());
  }

  @Test
  public void contextUpWithTransaction() {
    withTransaction(() -> {});
  }
}
