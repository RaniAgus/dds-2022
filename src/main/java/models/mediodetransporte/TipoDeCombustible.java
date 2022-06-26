package models.mediodetransporte;

import models.da.TipoDeConsumo;

public enum TipoDeCombustible {
  GNC(TipoDeConsumo.GAS_NATURAL), 
  NAFTA(TipoDeConsumo.NAFTA_MOVIL), 
  GASOIL(TipoDeConsumo.GASOIL_MOVIL),
  ELECTRICO(TipoDeConsumo.ELECTRICIDAD);

  private TipoDeConsumo tipoDeConsumo;

  TipoDeCombustible(TipoDeConsumo tipoDeConsumo) {
    this.tipoDeConsumo = tipoDeConsumo;
  }

  public Double factorEmision(){
    return tipoDeConsumo.getFactorEmision();
  }
}
