package mia;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static mia.factory.SectorFactory.unSectorVacio;
import static mia.factory.UbicacionFactory.medrano;
import static org.junit.jupiter.api.Assertions.*;

public class OrganizacionTest {

  @Test
  public void sePuedeCrearUnaOrganizacion() {
    Organizacion utn = utn();
    assertEquals("UTN", utn.getRazonSocial());
  }

  @Test
  public void sePuedeDarDeAltaUnSector() {
    Organizacion utn = utn();
    Sector unSectorVacio = unSectorVacio();
    utn.darDeAltaSector(unSectorVacio);
    assertTrue(utn.getSectores().contains(unSectorVacio));
  }

  private Organizacion utn() {
    return new Organizacion(
        "UTN",
        medrano(),
        TipoDeOrganizacion.INSTITUCION,
        ClasificacionDeOrganizacion.UNIVERSIDAD,
        emptyList()
    );
  }
}
