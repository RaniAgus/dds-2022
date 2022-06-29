package models.da;

public class TipoDeConsumo {
  private String nombre;
  private Double factorEmision;
  private UnidadDeConsumo unidadDeConsumo;

  public TipoDeConsumo(String nombre, Double factorEmision, UnidadDeConsumo unidadDeConsumo) {
    this.nombre = nombre;
    this.factorEmision = factorEmision;
    this.unidadDeConsumo = unidadDeConsumo;
  }

  public boolean tieneNombre(String nombre) {
    return this.nombre.equalsIgnoreCase(nombre);
  }

  public Double getFactorEmision() {
    return factorEmision;
  }

  public void setFactorEmision(Double factorEmision) {
    this.factorEmision = factorEmision;
  }
}
