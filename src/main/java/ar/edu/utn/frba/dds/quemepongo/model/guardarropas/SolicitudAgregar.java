package ar.edu.utn.frba.dds.quemepongo.model.guardarropas;

import ar.edu.utn.frba.dds.quemepongo.model.prenda.Prenda;

public class SolicitudAgregar extends SolicitudModificacion {
  private Prenda prenda;

  public SolicitudAgregar(Prenda prenda) {
    this.prenda = prenda;
  }

  @Override
  protected void realizarEn(Guardarropa guardarropa) {
    guardarropa.agregar(prenda);
  }

  @Override
  protected void revertirEn(Guardarropa guardarropa) {
    guardarropa.quitar(prenda);
  }
}
