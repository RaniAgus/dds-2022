package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TipoDeTransportePublico extends EntidadPersistente {
  private String nombre;
  private Double consumoPorKM;
  @ManyToOne
  private TipoDeConsumo tipoDeConsumo;

  public String getNombre() {
    return nombre;
  }

  public Double getConsumoPorKM() {
    return consumoPorKM;
  }

  public TipoDeConsumo getTipoDeConsumo() {
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
    return tipoDeConsumo.equals(tipo);
  }
}
