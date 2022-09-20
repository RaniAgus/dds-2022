package ar.edu.utn.frba.dds.impactoambiental.models.da;

import javax.persistence.*;

@Entity
public class DatoActividad {
  @Id
  @GeneratedValue
  private long id;
  @Embedded
  private TipoDeConsumo tipo;
  private Double valor;
  @Transient // @TODO
  private Periodo periodo;

  public DatoActividad(TipoDeConsumo tipo, Double valor, Periodo periodo) {
    this.tipo = tipo;
    this.valor = valor;
    this.periodo = periodo;
  }

  public Double getValor() {
    return valor;
  }

  public Double carbonoEquivalente() {
    return tipo.getFactorEmision() * valor;
  }

  public Boolean estaEnPeriodo(Periodo periodo) {
    return periodo.estaEnPeriodo(periodo);
  }

  public Boolean tieneTipoDeConsumo(TipoDeConsumo tipo) {
    return this.tipo.equals(tipo);
  }

}










