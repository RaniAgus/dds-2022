package ar.edu.utn.frba.dds.quemepongo.model.prenda.estado;

public class Sucia implements EstadoPrenda {
  private Integer usos;

  public Sucia(Integer usos) {
    this.usos = usos;
  }

  @Override
  public boolean esSugerible() {
    return true;
  }

  @Override
  public EstadoPrenda usar() {
    return ++usos > 3 ? new Percudida() : this;
  }

  @Override
  public EstadoPrenda lavar() {
    return new Usada();
  }
}
