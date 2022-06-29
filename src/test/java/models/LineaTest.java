package models;

import models.geolocalizacion.Distancia;
import models.mediodetransporte.Linea;
import models.mediodetransporte.Parada;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LineaTest extends BaseTest {

  // [TPA1]: Se debe permitir el alta de nuevas líneas férreas, subterráneas y de colectivos; así como también el alta
  // de paradas/estaciones de cada una de ellas.

  @Test
  public void sePuedeDarDeAltaParadas() {
    Linea linea = crearLineaDeSubteVacia();
    Parada medrano = crearParada(0, 34);

    linea.agregarParada(medrano, crearDistanciaEnKm(0));

    assertThat(linea.getParadas()).contains(medrano);
  }

  @Test
  public void alDarDeAltaUnaParadaSeCambiaLaDistanciaDeLaAnterior() {
    Linea linea = crearLineaDeSubteVacia();
    Parada medrano = crearParada(0, 0);
    Parada alem = crearParada(0, 0);

    linea.agregarParada(medrano, Distancia.CERO);
    linea.agregarParada(alem, crearDistanciaEnKm(50));

    assertThat(medrano.getDistanciaAProximaParada().getValor()).isEqualTo(50);
  }
}
