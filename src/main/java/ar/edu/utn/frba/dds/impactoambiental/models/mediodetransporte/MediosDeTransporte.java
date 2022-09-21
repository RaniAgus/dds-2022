package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;

public final class MediosDeTransporte implements Repositorio<MedioDeTransporte> {
  private static final MediosDeTransporte instance = new MediosDeTransporte();

  public static MediosDeTransporte getInstance() {
    return instance;
  }

  private MediosDeTransporte() {
  }

  @Override
  public Class<MedioDeTransporte> clase() {
    return MedioDeTransporte.class;
  }

}