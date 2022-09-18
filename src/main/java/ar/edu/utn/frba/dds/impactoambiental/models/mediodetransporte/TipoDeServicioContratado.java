package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

public class TipoDeServicioContratado {
  private String nombre;
  private TipoDeConsumo tipoDeConsumo;
  private Double consumoPorKM;

  public TipoDeServicioContratado(String nombre, TipoDeConsumo tipoDeConsumo, Double consumoPorKM) {
    this.nombre = nombre;
    this.tipoDeConsumo = tipoDeConsumo;
    this.consumoPorKM = consumoPorKM;
  }

  public Double carbonoEquivalentePorKM() {
    return tipoDeConsumo.getFactorEmision() * consumoPorKM;
  }

  public TipoDeConsumo getTipoDeConsumo() {
    return tipoDeConsumo;
  }
}
