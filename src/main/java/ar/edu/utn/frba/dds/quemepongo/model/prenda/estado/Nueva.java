package ar.edu.utn.frba.dds.quemepongo.model.prenda.estado;

public class Nueva implements EstadoPrenda {
  @Override
  public boolean esSugerible() {
    return true;
  }

  @Override
  public EstadoPrenda usar() {
    return new Usada();
  }

  @Override
  public EstadoPrenda lavar() {
    return this;
  }
}
