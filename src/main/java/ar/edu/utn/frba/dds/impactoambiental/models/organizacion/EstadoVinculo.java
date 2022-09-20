package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import javax.persistence.Embeddable;

@Embeddable
public enum EstadoVinculo {
  PENDIENTE {
    @Override
    public EstadoVinculo aceptar(Vinculacion vinculacion) {
      return EstadoVinculo.ACEPTADO;
    }

    @Override
    public EstadoVinculo rechazar(Vinculacion vinculacion) {
      return EstadoVinculo.RECHAZADO;
    }
  },
  ACEPTADO,
  RECHAZADO;

  public EstadoVinculo aceptar(Vinculacion vinculacion) {
    return this;
  }

  public EstadoVinculo rechazar(Vinculacion vinculacion) {
    return this;
  }
}
