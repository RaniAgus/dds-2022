package calendarios;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Horario {
  private LocalDateTime inicio;
  private LocalDateTime fin;

  public Horario(LocalDateTime inicio, LocalDateTime fin) {
    this.inicio = inicio;
    this.fin = fin;
  }

  public LocalDateTime getInicio() {
    return inicio;
  }

  public LocalDateTime getFin() {
    return fin;
  }

  public boolean estaAntesDe(LocalDateTime fecha) {
    return getInicio().isBefore(fecha);
  }

  public boolean estaEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return getInicio().isBefore(fin) && getFin().isAfter(inicio);
  }

  public boolean estaSolapadoCon(Horario horario) {
    return estaEntreFechas(horario.getInicio(), horario.getFin());
  }

  public Duration cuantoFalta() {
    return Duration.ofMinutes(LocalDateTime.now().until(getInicio(), ChronoUnit.MINUTES));
  }

  public Horario sumar(Duration duracion) {
    return new Horario(getInicio().plus(duracion), getFin().plus(duracion));
  }
}
