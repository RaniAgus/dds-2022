package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class TipoDeServicioContratado {
  @Id
  @GeneratedValue
  private long id;
  private String nombre;
  @Transient //@TODO
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
    if (tipoDeConsumo == null)
      return false;
    return tipoDeConsumo.equals(tipo);
  }
}
