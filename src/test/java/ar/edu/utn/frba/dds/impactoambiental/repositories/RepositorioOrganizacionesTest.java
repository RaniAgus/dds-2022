package ar.edu.utn.frba.dds.impactoambiental.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ar.edu.utn.frba.dds.impactoambiental.models.BaseTest;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Sector;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;

public class RepositorioOrganizacionesTest extends BaseTest implements PersistenceTest {
  RepositorioOrganizaciones repositorio = RepositorioOrganizaciones.getInstance();

  @Test
  public void sePuedeRecuperarUnaOrganizacionAPartirDeUnMiembro() {
    Miembro miembro = crearMiembro();
    Sector sector = crearSectorConUnaVinculacion(new Vinculacion(miembro));
    Organizacion organizacion = crearOrganizacionVacia();
    organizacion.getSectores().add(sector);

    repositorio.agregar(organizacion);

    Organizacion actualOrganizacion = repositorio.buscarPorMiembro(miembro).get();
    assertEquals(organizacion, actualOrganizacion);
  }
}
