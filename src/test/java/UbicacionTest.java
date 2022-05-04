import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UbicacionTest {
  @Test
  public void dadasDosUbicacionesSeCalculaLaDistanciaEnKilometrosEntreEllas() {
    Ubicacion medrano = new Ubicacion(
        "Estación Medrano",
        -34.60290512034288,
        -58.42095617309308
    );
    Ubicacion alem = new Ubicacion(
        "Estación L.N. Alem",
        -34.60295045162841,
        -58.37004823572711
    );

    assertEquals(4.66, medrano.distanciaA(alem), 0.01);
  }
}
