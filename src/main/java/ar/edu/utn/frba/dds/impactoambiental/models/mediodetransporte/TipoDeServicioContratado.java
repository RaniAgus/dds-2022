package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TipoDeServicioContratado extends EntidadPersistente {
  private String nombre;
  @ManyToOne
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

  public Boolean tieneTipoDeConsumo(TipoDeConsumo tipo) {
    return tipoDeConsumo.equals(tipo);
  }
}
