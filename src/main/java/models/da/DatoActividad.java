package models.da;

import java.time.LocalDate;

public class DatoActividad {
  private TipoDeConsumo tipo;
  private Double valor;
  private Periodicidad periodicidad;
  private String periodo;

  public DatoActividad(TipoDeConsumo tipo, Double valor, Periodicidad periodicidad, String periodo) {
    this.tipo = tipo;
    this.valor = valor;
    this.periodicidad = periodicidad;
    this.periodo = periodo;
  }

  public Double getValor() {
    return valor;
  }
  public Periodicidad getPeriodicidad() {
    return periodicidad;
  }
  public String getPeriodo() {
    return periodo;
  }
  public TipoDeConsumo getTipo() {
    return tipo;
  }

  public Double carbonoEquivalente() {
    return tipo.getFactorEmision() * valor;
  }

  public Boolean estaEnPeriodo(LocalDate fecha, Periodicidad periodicidad) {
    return true; // TODO: cambiar fecha DA a LocalDate e implementar funcion
  }
}