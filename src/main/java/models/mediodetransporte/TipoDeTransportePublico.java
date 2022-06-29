package models.mediodetransporte;

import models.da.TipoDeConsumo;

public enum TipoDeTransportePublico {
  TREN, SUBTE, COLECTIVO;

  private Double consumoPorKM;
  private TipoDeConsumo tipoDeConsumo;

  public Double getConsumoPorKM() {
    return consumoPorKM;
  }

  public void setConsumoPorKM(Double combustiblePorKM) {
    this.consumoPorKM = combustiblePorKM;
  }

  public Double carbonoEquivalentePorKM() {
    return tipoDeConsumo.getFactorEmision() * consumoPorKM;
  }
}
