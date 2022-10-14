package ar.edu.utn.frba.dds.impactoambiental.models.validador;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;

public final class RepositorioValidadores extends Repositorio<Validador> {
  private static final RepositorioValidadores instance = new RepositorioValidadores();

  public static RepositorioValidadores getInstance() {
    return instance;
  }

  private RepositorioValidadores() {
    super();
  }

  @Override
  public Class<Validador> clase() {
    return Validador.class;
  }
}
