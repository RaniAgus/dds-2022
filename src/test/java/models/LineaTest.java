package models;

import org.junit.jupiter.api.Test;
import static models.factory.LineaFactory.subteB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LineaTest {

  @Test
  public void sePuedeDarDeAltaNuevasLineas() {
    Linea linea = subteB();
    assertEquals("Subte B", linea.getNombre());
  }

  @Test
  public void sePuedeDarDeAltaParadas() {
    Linea linea = subteB();
    Ubicacion nuevaParada = new Ubicacion("El Origen", -34.58389828546913, -58.46665180454111);
    linea.agregarParada(nuevaParada);
    assertTrue(linea.getParadas().contains(nuevaParada));
  }
}
