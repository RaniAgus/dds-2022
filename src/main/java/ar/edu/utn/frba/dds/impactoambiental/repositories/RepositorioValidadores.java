package ar.edu.utn.frba.dds.impactoambiental.repositories;

import ar.edu.utn.frba.dds.impactoambiental.models.validador.Validador;

public final class RepositorioValidadores implements Repositorio<Validador> {
  private static final RepositorioValidadores instance = new RepositorioValidadores();

  public static RepositorioValidadores getInstance() {
    return instance;
  }

  private RepositorioValidadores() {}

  @Override
  public Class<Validador> clase() {
    return Validador.class;
  }
}
