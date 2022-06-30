package ar.edu.utn.frba.dds.quemepongo.model.prenda.estado;

public class EstadoSucia implements EstadoPrenda {
  private Integer usos;

  public EstadoSucia(Integer usos) {
    this.usos = usos;
  }

  @Override
  public boolean esSugerible() {
    return true;
  }

  @Override
  public EstadoPrenda usar() {
    return ++usos > 3 ? new EstadoPercudida() : this;
  }

  @Override
  public EstadoPrenda lavar() {
    return new EstadoUsada();
  }
}
