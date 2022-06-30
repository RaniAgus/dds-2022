package ar.edu.utn.frba.dds.quemepongo.model.prenda.estado;

public class EstadoNueva implements EstadoPrenda {
  @Override
  public boolean esSugerible() {
    return true;
  }

  @Override
  public EstadoPrenda usar() {
    return new EstadoUsada();
  }

  @Override
  public EstadoPrenda lavar() {
    return this;
  }
}
