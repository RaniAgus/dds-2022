package ar.edu.utn.frba.dds.impactoambiental.repositories;

import ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte.MedioDeTransporte;

public final class RepositorioMediosDeTransporte implements Repositorio<MedioDeTransporte> {
  private static final RepositorioMediosDeTransporte instance = new RepositorioMediosDeTransporte();

  public static RepositorioMediosDeTransporte getInstance() {
    return instance;
  }

  private RepositorioMediosDeTransporte() {}

  @Override
  public Class<MedioDeTransporte> clase() {
    return MedioDeTransporte.class;
  }

}