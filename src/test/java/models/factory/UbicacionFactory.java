package models.factory;

import models.Ubicacion;

import static org.mockito.Mockito.mock;

public class UbicacionFactory {
  public static Ubicacion medrano() {
    return mock(Ubicacion.class);
  }

  public static Ubicacion alem() {
    return mock(Ubicacion.class);
  }
}
