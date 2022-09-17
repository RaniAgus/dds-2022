package got;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import static org.junit.jupiter.api.Assertions.*;

public class RegionTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

  @Test
  public void puedePersistirUnaRegion() {
    Casa stark = new Casa(); // TODO inicializar demás atributos según sea necesario
    Lugar winterfell = new Castillo(10, 2);

    Region region = new Region("El norte", stark, Arrays.asList(winterfell));
    entityManager().persist(region);
  }
}
