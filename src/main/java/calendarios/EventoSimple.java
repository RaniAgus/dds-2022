package calendarios;

import calendarios.servicios.GugleMapas;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class EventoSimple implements Evento, Comparable<EventoSimple> {
  private GugleMapas gugleMapas;
  private String nombre;
  private Horario horario;
  private Ubicacion ubicacion;
  private List<Usuario> invitados;

  public EventoSimple(GugleMapas gugleMapas,
                      String nombre,
                      Horario horario,
                      Ubicacion ubicacion,
                      List<Usuario> invitados) {
    this.gugleMapas = gugleMapas;
    this.nombre = nombre;
    this.horario = horario;
    this.ubicacion = ubicacion;
    this.invitados = invitados;
  }

  private Ubicacion getUbicacion() {
    return ubicacion;
  }

  private Horario getHorario() {
    return horario;
  }

  public boolean tieneInvitado(Usuario usuario) {
    return invitados.contains(usuario);
  }

  @Override
  public boolean estaEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return getHorario().estaEntreFechas(inicio, fin);
  }

  @Override
  public boolean estaSolapadoCon(Evento otro) {
    return otro.eventosEntreFechas(getHorario().getInicio(), getHorario().getFin()).findAny().isPresent();
  }

  @Override
  public Stream<EventoSimple> eventosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return estaEntreFechas(inicio, fin) ? Stream.of(this) : Stream.empty();
  }

  @Override
  public Stream<EventoSimple> eventosSolapadosCon(Evento otro) {
    return estaSolapadoCon(otro) ? Stream.of(this) : Stream.empty();
  }

  @Override
  public Optional<EventoSimple> proximoEvento() {
    return cuantoFalta().isNegative() ? Optional.empty() : Optional.of(this);
  }

  @Override
  public boolean llegaATiempoDesde(Ubicacion ubicacion) {
    return gugleMapas.tiempoEstimadoHasta(ubicacion, getUbicacion()).compareTo(cuantoFalta()) <= 0;
  }

  @Override
  public Duration cuantoFalta() {
    return getHorario().cuantoFalta();
  }

  @Override
  public int compareTo(EventoSimple otro) {
    return cuantoFalta().compareTo(otro.cuantoFalta());
  }
}
