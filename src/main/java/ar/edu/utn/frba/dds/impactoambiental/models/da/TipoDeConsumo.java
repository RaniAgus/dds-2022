package ar.edu.utn.frba.dds.impactoambiental.models.da;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class TipoDeConsumo extends EntidadPersistente {
  private String nombre;
  private Double factorEmision;
  @Enumerated(EnumType.STRING)
  private UnidadDeConsumo unidadDeConsumo;

  protected TipoDeConsumo() {
  }

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
