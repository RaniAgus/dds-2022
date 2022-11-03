package ar.edu.utn.frba.dds.impactoambiental.repositories;

import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Vinculacion;

public class RepositorioVinculaciones implements Repositorio<Vinculacion> {
  private static RepositorioVinculaciones instance = new RepositorioVinculaciones();

  private RepositorioVinculaciones() {}

  public static RepositorioVinculaciones getInstance() {
    return instance;
  }

  @Override
  public Class<Vinculacion> clase() {
    return Vinculacion.class;
  }
}
