package got;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import static org.junit.jupiter.api.Assertions.*;

public class LugarTest extends AbstractPersistenceTest implements WithGlobalEntityManager, EntityManagerOps {
  private Castillo castillo;
  private Ciudad ciudad;

  @BeforeEach
  public void setup() {
    super.setup();
    castillo = new Castillo("Castillo", 1, 2, 3, 4);
    ciudad = new Ciudad("Ciudad", 1, 2, 3, 4, .5);
  }

  @AfterEach
  public void teardown() {
    super.tearDown();
  }

  @Test
  public void puedePersistirUnCastillo() {
    persist(castillo);
  }

  @Test
  public void puedePersistirUnaCiudad() {
    persist(ciudad);
  }

  @Test
  public void puedeRecuperarLugares() {
    persist(castillo);
		persist(ciudad);

    assertEquals(2, createQuery("from Lugar", Lugar.class).getResultList().size());
  }
}
