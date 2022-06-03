package models;

import models.geolocalizacion.Distancia;
import models.geolocalizacion.Unidad;
import models.mediodetransporte.Linea;
import models.mediodetransporte.Parada;
import org.junit.jupiter.api.Test;
import static models.factory.LineaFactory.subteB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

public class LineaTest {

  @Test
  public void sePuedeDarDeAltaParadas() {
    Linea linea = subteB();
    Parada nuevaParada = mock(Parada.class);

    linea.agregarParada(nuevaParada, new Distancia(BigDecimal.valueOf(0), Unidad.KM));

    assertThat(linea.getParadas()).contains(nuevaParada);
  }

  @Test
  public void alDarDeAltaUnaParadaSeCambiaLaDistanciaDeLaAnterior() {
    Linea linea = subteB();
    Parada primerParada = new Parada(new Distancia(BigDecimal.valueOf(0), Unidad.KM), "primera");
    linea.agregarParada(primerParada, new Distancia(BigDecimal.valueOf(0), Unidad.KM));

    Parada segundaParada = new Parada(new Distancia(BigDecimal.valueOf(0), Unidad.KM), "primera");
    linea.agregarParada(segundaParada, new Distancia(BigDecimal.valueOf(50), Unidad.KM));

    assertThat(primerParada.getDistanciaAProximaParada().getValor()).isEqualTo(50);
  }
}
