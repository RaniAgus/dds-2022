package models;

import models.mediodetransporte.Linea;
import org.junit.jupiter.api.Test;
import static models.factory.LineaFactory.subteB;
import static org.assertj.core.api.Assertions.assertThat;

public class LineaTest {

  @Test
  public void sePuedeDarDeAltaParadas() {
    Linea linea = subteB();
    Ubicacion nuevaParada = new Ubicacion("El Origen", -34.58389828546913, -58.46665180454111);

    linea.agregarParada(nuevaParada);

    assertThat(linea.getParadas()).contains(nuevaParada);
  }
}
