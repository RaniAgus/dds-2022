package models;

import org.junit.jupiter.api.Test;

import static models.factory.UbicacionFactory.alem;
import static models.factory.UbicacionFactory.medrano;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UbicacionTest {
  @Test
  public void dadasDosUbicacionesSeCalculaLaDistanciaEnKilometrosEntreEllas() {
    Ubicacion medrano = medrano();
    Ubicacion alem = alem();

    assertEquals(4.66, medrano.distanciaA(alem), 0.01);
  }
}
