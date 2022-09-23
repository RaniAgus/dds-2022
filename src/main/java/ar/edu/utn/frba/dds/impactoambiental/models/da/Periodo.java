package ar.edu.utn.frba.dds.impactoambiental.models.da;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Embeddable
public class Periodo {
  private LocalDate inicioPeriodo;
  @Enumerated(EnumType.STRING)
  private Periodicidad periodicidad;

  protected Periodo() {
  }

  public Periodo(LocalDate inicioPeriodo, Periodicidad periodicidad) {
    this.inicioPeriodo = inicioPeriodo;
    this.periodicidad = periodicidad;
  }

  public boolean contieneFecha(LocalDate fecha) {
    return (getInicioPeriodo().isBefore(fecha) || getInicioPeriodo().isEqual(fecha))
        && (getFinPeriodo().isAfter(fecha) || getFinPeriodo().isEqual(fecha));
  }

  public boolean estaEnPeriodo(Periodo periodo) {
    return contieneFecha(periodo.getInicioPeriodo()) || contieneFecha(periodo.getFinPeriodo());
  }

  public LocalDate getInicioPeriodo() {
    return inicioPeriodo;
  }

  private LocalDate getFinPeriodo() {
    return inicioPeriodo.plus(periodicidad.getPeriodo());
  }

  public Periodicidad getPeriodicidad() {
    return periodicidad;
  }
}
