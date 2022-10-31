package ar.edu.utn.frba.dds.impactoambiental.repositories;

import ar.edu.utn.frba.dds.impactoambiental.models.usuario.ValidacionDeUsuario;

public final class RepositorioValidacionesDeUsuario implements Repositorio <ValidacionDeUsuario> {
  private static RepositorioValidacionesDeUsuario instance = new RepositorioValidacionesDeUsuario();

  private RepositorioValidacionesDeUsuario() {}

  public static RepositorioValidacionesDeUsuario getInstance() {
    return instance;
  }

  @Override
  public Class<ValidacionDeUsuario> clase() {
    return ValidacionDeUsuario.class;
  }
}
