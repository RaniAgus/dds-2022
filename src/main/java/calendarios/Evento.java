package calendarios;

import calendarios.servicios.GugleMapas;
import calendarios.servicios.PositionService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Evento implements Comparable<Evento> {
  private GugleMapas gugleMapas;
  private String nombre;
  private LocalDateTime inicio;
  private LocalDateTime fin;
  private Ubicacion ubicacion;
  private List<Usuario> invitados;

  public Evento(GugleMapas gugleMapas,
                String nombre,
                LocalDateTime inicio,
                LocalDateTime fin,
                Ubicacion ubicacion,
                List<Usuario> invitados) {
    this.gugleMapas = gugleMapas;
    this.nombre = nombre;
    this.inicio = inicio;
    this.fin = fin;
    this.ubicacion = ubicacion;
    this.invitados = invitados;
  }

  public boolean tieneInvitado(Usuario usuario) {
    return invitados.contains(usuario);
  }

  public boolean estaEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return getInicio().isBefore(fin) && getFin().isAfter(inicio);
  }

  public boolean estaSolapadoCon(Evento otro) {
    return estaEntreFechas(otro.getInicio(), otro.getFin());
  }

  public Duration cuantoFalta() {
    return Duration.ofHours(LocalDateTime.now().until(getInicio(), ChronoUnit.HOURS));
  }

  public boolean llegaATiempoDesde(Ubicacion ubicacion) {
    return gugleMapas.tiempoEstimadoHasta(ubicacion, getUbicacion()).compareTo(cuantoFalta()) <= 0;
  }

  private LocalDateTime getInicio() {
    return inicio;
  }

  private LocalDateTime getFin() {
    return fin;
  }

  private Ubicacion getUbicacion() {
    return ubicacion;
  }

  @Override
  public int compareTo(Evento otro) {
    return getInicio().compareTo(otro.getInicio());
  }

  @Override
  public String toString() {
    return "Evento{" +
        ", nombre='" + nombre + '\'' +
        ", inicio=" + inicio +
        ", fin=" + fin +
        ", ubicacion=" + ubicacion +
        '}';
  }
}
