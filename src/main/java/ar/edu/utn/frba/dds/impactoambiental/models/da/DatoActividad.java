package ar.edu.utn.frba.dds.impactoambiental.models.da;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class DatoActividad extends EntidadPersistente {
  @ManyToOne
  private TipoDeConsumo tipoDeConsumo;
  private Double valor;
  @Embedded
  private Periodo periodo;

  protected DatoActividad() {
  }

  public DatoActividad(TipoDeConsumo tipoDeConsumo, Double valor, Periodo periodo) {
    this.tipoDeConsumo = tipoDeConsumo;
    this.valor = valor;
    this.periodo = periodo;
  }

  public Double getValor() {
    return valor;
  }

  public Double carbonoEquivalente() {
    return tipoDeConsumo.getFactorEmision() * valor;
  }

  public Boolean estaEnPeriodo(Periodo periodo) {
    return this.periodo.estaEnPeriodo(periodo);
  }

  public Boolean tieneTipoDeConsumo(TipoDeConsumo tipo) {
    return this.tipoDeConsumo.equals(tipo);
  }

}










