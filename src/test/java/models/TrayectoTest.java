package models;
import models.geolocalizacion.Distancia;
import models.geolocalizacion.Unidad;
import models.miembro.Miembro;
import models.miembro.Tramo;
import models.miembro.Trayecto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static models.factory.MiembroFactory.agus;
import static models.factory.TramoFactory.tramoAPieDesdeMedranoHastaAlem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrayectoTest {
  @Test
  public void sePuedeDarDeAltaUnNuevoTrayecto() {
    Miembro miembro = agus();
    Trayecto trayecto = new Trayecto(singletonList(tramoAPieDesdeMedranoHastaAlem()), singletonList(miembro));
    assertThat(trayecto.getViajantes()).containsExactly(miembro);
  }

  @Test
  public void laDistanciaDeUnTrayectoEsLaSumaDeSusTramos() {
    Tramo primerTramo = mock(Tramo.class);
    Tramo segundoTramo = mock(Tramo.class);
    when(primerTramo.esCompartible()).thenReturn(true);
    when(segundoTramo.esCompartible()).thenReturn(true);
    when(primerTramo.getDistancia()).thenReturn(new Distancia(new BigDecimal(50), Unidad.KM));
    when(segundoTramo.getDistancia()).thenReturn(new Distancia(new BigDecimal(200), Unidad.KM));

    Trayecto trayectoTest = new Trayecto(asList(primerTramo, segundoTramo), emptyList());
    assertEquals(250, trayectoTest.medirDistanciaTrayecto().getValor());
  }
}
