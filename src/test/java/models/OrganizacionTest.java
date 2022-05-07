package models;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static models.factory.SectorFactory.unSectorVacio;
import static models.factory.UbicacionFactory.medrano;
import static org.assertj.core.api.Assertions.assertThat;

public class OrganizacionTest {

  @Test
  public void sePuedeDarDeAltaUnSector() {
    Organizacion utn = utn();
    Sector unSectorVacio = unSectorVacio();

    utn.darDeAltaSector(unSectorVacio);

    assertThat(utn.getSectores()).containsExactly(unSectorVacio);
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
