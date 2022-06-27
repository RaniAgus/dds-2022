package models;

import models.miembro.Miembro;
import models.miembro.Tramo;
import models.miembro.Trayecto;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TrayectoTest extends BaseTest {
  @Test
  public void sePuedeDarDeAltaUnNuevoTrayecto() {
    Tramo tramo = crearTramoEnBicicleta(utnMedrano, utnCampus);
    Trayecto trayecto = new Trayecto(singletonList(tramo));
    assertThat(trayecto.getTramos()).containsExactly(tramo);
  }

  @Test
  public void laDistanciaDeUnTrayectoEsLaSumaDeSusTramos() {
    when(geolocalizador.medirDistancia(utnMedrano, utnCampus)).thenReturn(crearDistanciaEnKm(20));
    when(geolocalizador.medirDistancia(utnCampus, utnMedrano)).thenReturn(crearDistanciaEnKm(30));

    Tramo primerTramo = crearTramoEnBicicleta(utnMedrano, utnCampus);
    Tramo segundoTramo = crearTramoEnBicicleta(utnCampus, utnMedrano);
    Trayecto trayecto = new Trayecto(asList(primerTramo, segundoTramo));

    assertEquals(crearDistanciaEnKm(50), trayecto.getDistancia());
  }
}
