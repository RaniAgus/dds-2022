package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.Repositorio;

public final class Lineas implements Repositorio<Linea> {
  private static final Lineas instance = new Lineas();

  public static Lineas getInstance() {
    return instance;
  }

  private Lineas() {
  }

  @Override
  public Class<Linea> clase() {
    return Linea.class;
  }

}