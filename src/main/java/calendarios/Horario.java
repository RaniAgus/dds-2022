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

  public boolean estaEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return getInicio().isBefore(fin) && getFin().isAfter(inicio);
  }

  public boolean estaSolapadoCon(Horario horario) {
    return estaEntreFechas(horario.getInicio(), horario.getFin());
  }

  public Duration cuantoFalta() {
    return Duration.ofHours(LocalDateTime.now().until(getInicio(), ChronoUnit.MINUTES));
  }

  @Override
  public String toString() {
    return "Horario{" +
        "inicio=" + inicio +
        ", fin=" + fin +
        '}';
  }
}
