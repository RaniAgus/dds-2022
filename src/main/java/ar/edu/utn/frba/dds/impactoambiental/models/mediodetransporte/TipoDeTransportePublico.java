package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

public enum TipoDeTransportePublico {
  TREN, SUBTE, COLECTIVO;

  private Double consumoPorKM;
  private TipoDeConsumo tipoDeConsumo;

  public Double getConsumoPorKM() {
    if (consumoPorKM == null) {
      throw new IllegalStateException("El dato de consumo por KM para " + this.toString() + " no fue cargado.");
    }
    return consumoPorKM;
  }

  public TipoDeConsumo getTipoDeConsumo() {
    if (tipoDeConsumo == null) {
      throw new IllegalStateException("El tipo de consumo para " + this.toString() + " no fue cargado.");
    }
    return tipoDeConsumo;
  }

  public void setTipoDeConsumo(TipoDeConsumo tipoDeConsumo) {
    this.tipoDeConsumo = tipoDeConsumo;
  }

  public void setConsumoPorKM(Double combustiblePorKM) {
    this.consumoPorKM = combustiblePorKM;
  }

  public Double carbonoEquivalentePorKM() {
    return tipoDeConsumo.getFactorEmision() * consumoPorKM;
  }

  public Boolean tieneTipoDeConsumo(TipoDeConsumo tipo) {
    if (tipoDeConsumo == null)
      return false;
    return tipoDeConsumo.equals(tipo);
  }

}
