package models;

import models.geolocalizacion.Ubicacion;
import models.mediodetransporte.Linea;
import org.junit.jupiter.api.Test;
import static models.factory.LineaFactory.subteB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class LineaTest {

  @Test
  public void sePuedeDarDeAltaParadas() {
    Linea linea = subteB();
    Ubicacion nuevaParada = mock(Ubicacion.class);

    linea.agregarParada(nuevaParada);

    assertThat(linea.getParadas()).contains(nuevaParada);
  }
}
