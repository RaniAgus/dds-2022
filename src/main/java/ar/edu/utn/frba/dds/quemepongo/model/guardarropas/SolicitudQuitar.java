package ar.edu.utn.frba.dds.quemepongo.model.guardarropas;

import ar.edu.utn.frba.dds.quemepongo.model.prenda.Prenda;

public class SolicitudQuitar extends SolicitudModificacion {
  private Prenda prenda;

  public SolicitudQuitar(Prenda prenda) {
    this.prenda = prenda;
  }

  @Override
  protected void realizarEn(Guardarropas guardarropas) {
    guardarropas.quitar(prenda);
  }

  @Override
  protected void revertirEn(Guardarropas guardarropas) {
    guardarropas.agregar(prenda);
  }
}
