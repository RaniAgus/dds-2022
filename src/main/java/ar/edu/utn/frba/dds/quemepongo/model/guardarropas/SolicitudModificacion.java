package ar.edu.utn.frba.dds.quemepongo.model.guardarropas;

public abstract class SolicitudModificacion {
  private EstadoModificacion estado = EstadoModificacion.PENDIENTE;

  public boolean estaEnEstado(EstadoModificacion estado) {
    return this.estado.equals(estado);
  }

  public void aceptarEn(Guardarropas guardarropas) {
    realizarEn(guardarropas);
    estado = EstadoModificacion.ACEPTADA;
  }

  public void rechazar() {
    estado = EstadoModificacion.RECHAZADA;
  }

  public void deshacerEn(Guardarropas guardarropas) {
    revertirEn(guardarropas);
    estado = EstadoModificacion.RECHAZADA;
  }

  protected abstract void realizarEn(Guardarropas guardarropas);
  protected abstract void revertirEn(Guardarropas guardarropas);
}
