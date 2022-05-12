package ar.edu.utn.frba.dds.quemepongo.model.estado;

public class Usada implements Estado {
  @Override
  public boolean esSugerible() {
    return true;
  }

  @Override
  public Estado usar() {
    return new Sucia(0);
  }

  @Override
  public Estado lavar() {
    return this;
  }
}
