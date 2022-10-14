package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;

public final class RepositorioDeLineas extends Repositorio<Linea> {
  private static final RepositorioDeLineas instance = new RepositorioDeLineas();

  public static RepositorioDeLineas getInstance() {
    return instance;
  }

  private RepositorioDeLineas() {
    super();
  }

  @Override
  public Class<Linea> clase() {
    return Linea.class;
  }

}
