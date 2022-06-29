package models;

import models.geolocalizacion.Unidad;
import models.miembro.Tramo;
import models.miembro.Trayecto;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TrayectoTest extends BaseTest {

  // [TPA1]: Se debe permitir el alta de trayectos teniendo en cuenta que cada uno de éstos puede contener varios tramos.

  @Test
  public void sePuedeDarDeAltaUnNuevoTrayecto() {
    Tramo tramo = crearTramoEnBicicleta(utnMedrano, utnCampus);
    Trayecto trayecto = crearTrayectoConTramos(singletonList(tramo));

    assertThat(trayecto.getTramos()).containsExactly(tramo);
  }

  // [TPA2]: Se debe permitir dar a conocer la distancia total de un trayecto.

  @Test
  public void laDistanciaDeUnTrayectoEsLaSumaDeSusTramos() {
    when(geolocalizador.medirDistancia(utnMedrano, utnCampus)).thenReturn(crearDistanciaEnKm(20));
    when(geolocalizador.medirDistancia(utnCampus, utnMedrano)).thenReturn(crearDistanciaEnKm(30));

    Trayecto trayecto = crearTrayectoConTramos(asList(
        crearTramoEnBicicleta(utnMedrano, utnCampus),
        crearTramoEnBicicleta(utnCampus, utnMedrano)
    ));

    assertThat(trayecto.getDistancia())
        .extracting("valor", "unidad")
        .containsExactly(50, Unidad.KM);
  }

  // [TPA2]: Se debe permitir la carga de Trayectos compartidos entre miembros de una organización.

  @Test
  public void unTrayectoEsCompartibleCuandoTodosSusTramosLoSon() {
    Trayecto trayecto = crearTrayectoConTramos(asList(
        crearTramoEnServicioContratado(),
        crearTramoEnVehiculoParticular()
    ));

    assertThat(trayecto.esCompartible()).isTrue();
  }

  @Test
  public void unTrayectoNoEsCompartibleCuandoUnTramoNoLoEs() {
    Trayecto trayecto = crearTrayectoConTramos(asList(
        crearTramoEnBicicleta(utnCampus, utnMedrano),
        crearTramoEnVehiculoParticular()
    ));

    assertThat(trayecto.esCompartible()).isFalse();
  }
}
