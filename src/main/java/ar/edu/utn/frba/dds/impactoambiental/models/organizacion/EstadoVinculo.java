package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

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

  private String nombre;

  EstadoVinculo() {
    this.nombre = this.name();
  }

  public EstadoVinculo aceptar(Vinculacion vinculacion) {
    return this;
  }

  public EstadoVinculo rechazar(Vinculacion vinculacion) {
    return this;
  }

  public String getNombre() {
    return this.name();
  }
}
