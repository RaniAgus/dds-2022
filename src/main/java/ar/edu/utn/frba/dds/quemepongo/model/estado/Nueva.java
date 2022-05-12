package ar.edu.utn.frba.dds.quemepongo.model.estado;

public class Nueva implements Estado {
  @Override
  public boolean esSugerible() {
    return true;
  }

  @Override
  public Estado usar() {
    return new Usada();
  }

  @Override
  public Estado lavar() {
    return this;
  }
}
