package ar.edu.utn.frba.dds.quemepongo.model.prenda.estado;

public class EstadoPercudida implements EstadoPrenda {
  @Override
  public boolean esSugerible() {
    return false;
  }

  @Override
  public EstadoPrenda usar() {
    return this;
  }

  @Override
  public EstadoPrenda lavar() {
    return this;
  }
}
