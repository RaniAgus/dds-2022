package ar.edu.utn.frba.dds.quemepongo.model.prenda.estado;

public class Usada implements EstadoPrenda {
  @Override
  public boolean esSugerible() {
    return true;
  }

  @Override
  public EstadoPrenda usar() {
    return new Sucia(0);
  }

  @Override
  public EstadoPrenda lavar() {
    return this;
  }
}
