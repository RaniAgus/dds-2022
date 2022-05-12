package ar.edu.utn.frba.dds.quemepongo.model.estado;

public class Sucia implements Estado {
  private Integer usos;

  public Sucia(Integer usos) {
    this.usos = usos;
  }

  @Override
  public boolean esSugerible() {
    return true;
  }

  @Override
  public Estado usar() {
    return ++usos > 3 ? new Percudida() : this;
  }

  @Override
  public Estado lavar() {
    return new Usada();
  }
}
