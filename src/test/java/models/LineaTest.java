package models;

import models.mediodetransporte.Linea;
import models.mediodetransporte.Parada;
import org.junit.jupiter.api.Test;
import static models.factory.LineaFactory.subteB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class LineaTest {

  @Test
  public void sePuedeDarDeAltaParadas() {
    Linea linea = subteB();
    Parada nuevaParada = mock(Parada.class);

    linea.agregarParada(nuevaParada);

    assertThat(linea.getParadas()).contains(nuevaParada);
  }
}
