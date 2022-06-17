package ar.edu.utn.frba.dds.quemepongo.model.usuario;

import ar.edu.utn.frba.dds.quemepongo.model.prenda.Prenda;

public class SolicitudQuitar extends SolicitudModificacion {
  private Prenda prenda;

  public SolicitudQuitar(Prenda prenda) {
    this.prenda = prenda;
  }

  @Override
  protected void realizarEn(Guardarropa guardarropa) {
    guardarropa.quitar(prenda);
  }

  @Override
  protected void revertirEn(Guardarropa guardarropa) {
    guardarropa.agregar(prenda);
  }
}
