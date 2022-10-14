package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;

public final class RepositorioMediosDeTransporte extends Repositorio<MedioDeTransporte> {
  private static final RepositorioMediosDeTransporte instance = new RepositorioMediosDeTransporte();

  public static RepositorioMediosDeTransporte getInstance() {
    return instance;
  }

  private RepositorioMediosDeTransporte() {
    super();
  }

  @Override
  public Class<MedioDeTransporte> clase() {
    return MedioDeTransporte.class;
  }

}