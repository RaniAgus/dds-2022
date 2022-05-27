package models;
import models.geolocalizacion.Distancia;
import models.geolocalizacion.Ubicacion;
import models.geolocalizacion.Unidad;
import models.miembro.Miembro;
import models.miembro.Tramo;
import models.miembro.Trayecto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrayectoTest {
  @Test
  public void laDistanciaDeUnTrayectoEsLaSumaDeSusTramos() {
    Tramo primerTramo = mock(Tramo.class);
    Tramo segundoTramo = mock(Tramo.class);
    when(primerTramo.esCompartible()).thenReturn(true);
    when(segundoTramo.esCompartible()).thenReturn(true);
    when(primerTramo.getDistancia()).thenReturn(new Distancia(new BigDecimal(50), Unidad.KM));
    when(segundoTramo.getDistancia()).thenReturn(new Distancia(new BigDecimal(200), Unidad.KM));

    Trayecto trayectoTest = new Trayecto(asList(primerTramo,segundoTramo),mock(List.class));
    assertEquals(250,trayectoTest.medirDistanciaTrayecto().getValor());
  }
}
