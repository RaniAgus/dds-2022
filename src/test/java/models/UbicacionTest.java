package models;

import org.junit.jupiter.api.Test;

import static models.factory.UbicacionFactory.alem;
import static models.factory.UbicacionFactory.medrano;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class UbicacionTest {
  @Test
  public void dadasDosUbicacionesSeCalculaLaDistanciaEnKilometrosEntreEllas() {
    Ubicacion medrano = medrano();
    Ubicacion alem = alem();

    assertThat(medrano.distanciaA(alem)).isCloseTo(4.66, offset(0.01));
  }
}
