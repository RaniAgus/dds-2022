package ar.edu.utn.frba.dds.quemepongo.model.prenda.estado;

public class EstadoUsada implements EstadoPrenda {
  @Override
  public boolean esSugerible() {
    return true;
  }

  @Override
  public EstadoPrenda usar() {
    return new EstadoSucia(0);
  }

  @Override
  public EstadoPrenda lavar() {
    return this;
  }
}
