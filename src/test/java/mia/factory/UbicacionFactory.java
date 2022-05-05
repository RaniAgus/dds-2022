package mia.factory;

import mia.Ubicacion;

public class UbicacionFactory {
  public static Ubicacion medrano() {
    return new Ubicacion(
        "Estación Medrano",
        -34.60290512034288,
        -58.42095617309308
    );
  }

  public static Ubicacion alem() {
    return new Ubicacion(
        "Estación L.N. Alem",
        -34.60295045162841,
        -58.37004823572711
    );
  }
}
