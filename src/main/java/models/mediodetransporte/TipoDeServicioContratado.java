package models.mediodetransporte;

import models.da.TipoDeConsumo;

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
}
