package ar.edu.utn.frba.dds.quemepongo.model.estado;

public class Percudida implements Estado {
  @Override
  public boolean esSugerible() {
    return false;
  }

  @Override
  public Estado usar() {
    return this;
  }

  @Override
  public Estado lavar() {
    return this;
  }
}
