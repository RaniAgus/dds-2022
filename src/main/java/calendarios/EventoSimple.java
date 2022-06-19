package calendarios;

import calendarios.servicios.GugleMapas;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class EventoSimple implements Evento {
  private GugleMapas gugleMapas;
  private String nombre;
  private Horario horario;
  private Ubicacion ubicacion;
  private List<Recordatorio> recordatorios;
  private List<Usuario> invitados;

  public EventoSimple(GugleMapas gugleMapas,
                      String nombre,
                      Horario horario,
                      Ubicacion ubicacion,
                      List<Recordatorio> recordatorios,
                      List<Usuario> invitados) {
    this.gugleMapas = gugleMapas;
    this.nombre = nombre;
    this.horario = horario;
    this.ubicacion = ubicacion;
    this.recordatorios = recordatorios;
    this.invitados = invitados;
  }

  public String getNombre() {
    return nombre;
  }

  private Ubicacion getUbicacion() {
    return ubicacion;
  }

  private Horario getHorario() {
    return horario;
  }

  public List<Usuario> getInvitados() {
    return invitados;
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
    return otro.eventosEntreFechas(getHorario().getInicio(), getHorario().getFin())
        .findAny().isPresent();
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
  public void enviarRecordatorios(Usuario owner) {
    recordatorios.stream()
        .filter(it -> it.debeSerEnviado(cuantoFalta()))
        .forEach(it -> it.enviar(owner, this));
  }

  public int compararProximo(EventoSimple otro) {
    return cuantoFalta().compareTo(otro.cuantoFalta());
  }
}
