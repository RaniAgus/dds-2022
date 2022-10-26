package ar.edu.utn.frba.dds.impactoambiental.repositories;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;

public class RepositorioOrganizaciones implements Repositorio<Organizacion> {
  private static final RepositorioOrganizaciones instance = new RepositorioOrganizaciones();

  public static RepositorioOrganizaciones getInstance() {
    return instance;
  }

  private RepositorioOrganizaciones() {}

  @Override
  public Class<Organizacion> clase() {
    return Organizacion.class;
  }
}
