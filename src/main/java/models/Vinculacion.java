package models;

public class Vinculacion {
  Miembro miembro;
  EstadoVinculo estado;

  public Vinculacion(Miembro miembro) {
    this.miembro = miembro;
    this.estado = EstadoVinculo.PENDIENTE;
  }

  public Miembro getMiembro() {
    return miembro;
  }

  public EstadoVinculo getEstado() {
    return estado;
  }

  public void setEstado(EstadoVinculo estado) {
    this.estado = estado;
  }
}

