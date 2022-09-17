package got;

import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class RegionTest extends AbstractPersistenceTest implements WithGlobalEntityManager, EntityManagerOps {

  @BeforeEach
  public void setup() {
    super.setup();
  }

  @AfterEach
  public void teardown() {
    super.tearDown();
  }

  @Test
  public void puedePersistirUnaRegion() {
    Lugar winterfell = new Castillo("Winterfell", -8000, 100, 10, 2);
    Casa stark = new Casa("Stark", 100, 1000, null, winterfell, Collections.emptySet());
    Region region = new Region("El norte", stark, Collections.singletonList(winterfell));

    persist(region);
  }
}
