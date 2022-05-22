package models.da;

public enum TipoDeConsumo {
  GAS_NATURAL(1.0),
  DIESEL(1.0),
  GASOIL(1.0),
  NAFTA(1.0),
  CARBON(1.0),
  GASOIL_MOVIL(1.0),
  NAFTA_MOVIL(1.0),
  ELECTRICIDAD(1.0),
  RECORRIDO_CAMION_CARGA(1.0),
  RECORRIDO_UTILITARIO_LIVIANO(1.0);

  private Double factorEmision;
  
  private TipoDeConsumo(Double factorEmision) {
    this.factorEmision = factorEmision;
  }

  public Double getFactorEmision() {
    return factorEmision;
  }
  
  public void setFactorEmision(Double factorEmision) {
    this.factorEmision = factorEmision;
  }
}
