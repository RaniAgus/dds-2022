package ar.edu.utn.frba.dds.impactoambiental.models.da;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

public class Periodo {
  private LocalDate inicioPeriodo;
  @Embedded
  @Enumerated(EnumType.STRING)
  private Periodicidad periodicidad;

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

  private LocalDate getInicioPeriodo() {
    return inicioPeriodo;
  }

  private LocalDate getFinPeriodo() {
    return inicioPeriodo.plus(periodicidad.getPeriodo());
  }
}
