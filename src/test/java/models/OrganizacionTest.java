package models;

import models.organizacion.Organizacion;
import models.organizacion.Sector;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrganizacionTest extends BaseTest {
  @Test
  public void sePuedeDarDeAltaUnSector() {
    Organizacion utn = crearOrganizacionVacia();
    Sector unSectorVacio = crearSectorVacio();

    utn.darDeAltaSector(unSectorVacio);

    assertThat(utn.getSectores()).containsExactly(unSectorVacio);
  }
}
