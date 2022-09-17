package got;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import static org.junit.jupiter.api.Assertions.*;

public class LugarTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

  @Test
  public void puedePersistirUnCastillo() {
    entityManager().persist(new Castillo(10, 2));
  }

  @Test
  public void puedePersistirUnaCiudad() {
    entityManager().persist(new Ciudad()); // TODO inicializar resto de los atributos según corresponda
  }

  @Test
  public void puedeRecuperarLugares() {
    entityManager().persist(new Castillo(10, 2));
		entityManager().persist(new Ciudad());  // TODO inicializar resto de los atributos según corresponda

    assertEquals(2, entityManager().createQuery("from Lugares", Lugar.class).getResultList().size());
  }
}
