package ar.edu.utn.frba.dds.impactoambiental.models.da;

public class DatoActividad {
  private TipoDeConsumo tipo;
  private Double valor;
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

  public TipoDeConsumo getTipoDeConsumo() {
    return tipo;
  }

}










