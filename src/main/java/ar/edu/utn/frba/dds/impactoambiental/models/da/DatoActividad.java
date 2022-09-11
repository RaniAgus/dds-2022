package ar.edu.utn.frba.dds.impactoambiental.models.da;

import java.time.LocalDate;

public class DatoActividad {
  private TipoDeConsumo tipo;
  private Double valor;
  private Periodicidad periodicidad;
  private LocalDate inicioPeriodo;

  public DatoActividad(TipoDeConsumo tipo, Double valor, Periodicidad periodicidad, LocalDate inicioPeriodo) {
    this.tipo = tipo;
    this.valor = valor;
    this.periodicidad = periodicidad;
    this.inicioPeriodo = inicioPeriodo;
  }

  public Double getValor() {
    return valor;
  }
  public Periodicidad getPeriodicidad() {
    return periodicidad;
  }
  public LocalDate getPeriodo() {
    return inicioPeriodo;
  }
  public TipoDeConsumo getTipo() {
    return tipo;
  }

  public Double carbonoEquivalente() {
    return tipo.getFactorEmision() * valor;
  }

  public Boolean estaEnPeriodo(LocalDate fecha, Periodicidad periodicidad) {
    if (fecha.getYear() != inicioPeriodo.getYear()) { return false; }
    if (periodicidad == Periodicidad.ANUAL) { return true; }
    if (this.periodicidad == Periodicidad.ANUAL) { return false; }
    return fecha.getMonthValue() == inicioPeriodo.getMonthValue();
  }

}










