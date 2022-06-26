package models.da;
public enum TipoDeConsumo {
  GAS_NATURAL(UnidadDeConsumo.M3),
  DIESEL(UnidadDeConsumo.LT),
  GASOIL(UnidadDeConsumo.LT),
  NAFTA(UnidadDeConsumo.LT),
  CARBON(UnidadDeConsumo.KG),
  GASOIL_MOVIL(UnidadDeConsumo.LTS),
  NAFTA_MOVIL(UnidadDeConsumo.LTS),
  ELECTRICIDAD(UnidadDeConsumo.KWH),
  RECORRIDO_CAMION_CARGA(UnidadDeConsumo.KM),
  RECORRIDO_UTILITARIO_LIVIANO(UnidadDeConsumo.KM);

  private Double factorEmision;
  private final UnidadDeConsumo unidadDeConsumo;

  TipoDeConsumo(UnidadDeConsumo unidadDeConsumo) {
    this.unidadDeConsumo = unidadDeConsumo;
  }

  public Double getFactorEmision() {
    return factorEmision;
  }

  public void setFactorEmision(Double factorEmision) {
    this.factorEmision = factorEmision;
  }
}
