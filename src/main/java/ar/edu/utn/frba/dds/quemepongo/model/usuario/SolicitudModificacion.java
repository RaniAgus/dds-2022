package ar.edu.utn.frba.dds.quemepongo.model.usuario;

public abstract class SolicitudModificacion {
  private EstadoModificacion estado = EstadoModificacion.PENDIENTE;

  public boolean estaEnEstado(EstadoModificacion estado) {
    return this.estado.equals(estado);
  }

  public void aceptarEn(Guardarropa guardarropa) {
    realizarEn(guardarropa);
    estado = EstadoModificacion.ACEPTADA;
  }

  public void rechazar() {
    estado = EstadoModificacion.RECHAZADA;
  }

  public void deshacerEn(Guardarropa guardarropa) {
    revertirEn(guardarropa);
    estado = EstadoModificacion.RECHAZADA;
  }

  protected abstract void realizarEn(Guardarropa guardarropa);
  protected abstract void revertirEn(Guardarropa guardarropa);
}
