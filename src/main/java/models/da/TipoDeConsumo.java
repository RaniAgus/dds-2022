package models.da;
public enum TipoDeConsumo {
  GAS_NATURAL(1.0, UnidadDeConsumo.M3),
  DIESEL(1.0, UnidadDeConsumo.LT),
  GASOIL(1.0, UnidadDeConsumo.LT),
  NAFTA(1.0, UnidadDeConsumo.LT),
  CARBON(1.0, UnidadDeConsumo.KG),
  GASOIL_MOVIL(1.0, UnidadDeConsumo.LTS),
  NAFTA_MOVIL(1.0, UnidadDeConsumo.LTS),
  ELECTRICIDAD(1.0, UnidadDeConsumo.KWH),
  RECORRIDO_CAMION_CARGA(1.0, UnidadDeConsumo.KM),
  RECORRIDO_UTILITARIO_LIVIANO(1.0, UnidadDeConsumo.KM);

  private Double factorEmision;
  private final UnidadDeConsumo unidadDeConsumo;

  TipoDeConsumo(Double factorEmision, UnidadDeConsumo unidadDeConsumo) {
    this.factorEmision = factorEmision;
    this.unidadDeConsumo = unidadDeConsumo;
  }

  public Double getFactorEmision() {
    return factorEmision;
  }

  public void setFactorEmision(Double factorEmision) {
    this.factorEmision = factorEmision;
  }
}
