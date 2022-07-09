package models;

import models.da.Periodicidad;
import models.miembro.Miembro;
import models.miembro.TramoPrivado;
import models.miembro.Trayecto;
import models.organizacion.EstadoVinculo;
import models.organizacion.Organizacion;
import models.organizacion.Sector;
import models.organizacion.Vinculacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Collections;

public class CalculoHCTest extends BaseTest {
  @Test
  public void laHCDeUnTrayectoCompartidoSeComputaUnaVez() {
    Miembro m1 = crearMiembro();
    Miembro m2 = crearMiembro();

    TramoPrivado tramo = Mockito.mock(TramoPrivado.class);
    Mockito.when(tramo.esCompartible()).thenReturn(Boolean.TRUE);
    Mockito.when(tramo.carbonoEquivalente()).thenReturn(10.0);

    Trayecto trayectoCompartido = crearTrayectoConTramos(Collections.singletonList(tramo));

    Sector sector = crearSectorVacio();
    sector.solicitarVinculacion(crearVinculacionPendiente(m1));
    sector.solicitarVinculacion(crearVinculacionPendiente(m2));
    sector.getVinculacionesSegunEstado(EstadoVinculo.PENDIENTE).forEach(Vinculacion::aceptar);

    m1.darDeAltaTrayecto(trayectoCompartido);
    m2.darDeAltaTrayecto(trayectoCompartido);

    Organizacion org = crearOrganizacionVacia();
    org.darDeAltaSector(sector);

    Assertions.assertEquals(200, org.huellaCarbono(LocalDate.now(), Periodicidad.MENSUAL));

  }


}
