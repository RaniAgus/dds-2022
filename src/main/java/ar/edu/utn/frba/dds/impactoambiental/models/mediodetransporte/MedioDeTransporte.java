package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.*;

@Entity
public class MedioDeTransporte extends EntidadPersistente {
  private String nombre;
  private Double consumoPorKm;
  @ManyToOne(cascade= CascadeType.ALL)
  private TipoDeConsumo tipoDeConsumo;
  @Enumerated(EnumType.STRING)
  private TipoDeTransporte tipoDeTransporte;

  protected MedioDeTransporte() {
  }

  public MedioDeTransporte(String nombre,
                           Double consumoPorKm,
                           TipoDeConsumo tipoDeConsumo,
                           TipoDeTransporte tipoDeTransporte) {
    this.nombre = nombre;
    this.consumoPorKm = consumoPorKm;
    this.tipoDeConsumo = tipoDeConsumo;
    this.tipoDeTransporte = tipoDeTransporte;
  }

  public String getNombre() {
    return nombre;
  }

  public Double carbonoEquivalentePorKM() {
    return tipoDeConsumo != null ? tipoDeConsumo.getFactorEmision() * consumoPorKm : 0;
  }

  public boolean esCompartible() {
    return tipoDeTransporte.esCompartible();
  }

  public Boolean tieneTipoDeConsumo(TipoDeConsumo tipo) {
    return tipoDeConsumo != null && tipoDeConsumo.equals(tipo);
  }
}
