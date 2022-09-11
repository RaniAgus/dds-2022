package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;

public class Vinculacion {
  private Miembro miembro;
  private EstadoVinculo estado;

  public Vinculacion(Miembro miembro) {
    this.miembro = miembro;
    this.estado = EstadoVinculo.PENDIENTE;
  }

  public void aceptar() {
    this.estado = this.estado.aceptar(this);
  }

  public void rechazar() {
    this.estado = this.estado.rechazar(this);
  }

  public Miembro getMiembro() {
    return miembro;
  }

  public EstadoVinculo getEstado() {
    return estado;
  }

}

