package calendarios;

import com.google.common.collect.Iterables;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class EventoRecurrente implements Evento {
  private List<EventoSimple> repeticiones;

  public EventoRecurrente(List<EventoSimple> repeticiones) {
    this.repeticiones = repeticiones;
  }

  @Override
  public boolean estaEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return eventosEntreFechas(inicio, fin).findAny().isPresent();
  }

  @Override
  public boolean estaSolapadoCon(Evento otro) {
    return eventosSolapadosCon(otro).findAny().isPresent();
  }

  @Override
  public Stream<EventoSimple> eventosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return repeticiones.stream().flatMap(it -> it.eventosEntreFechas(inicio, fin));
  }

  @Override
  public Stream<EventoSimple> eventosSolapadosCon(Evento otro) {
    return repeticiones.stream().flatMap(it -> it.eventosSolapadosCon(otro));
  }

  @Override
  public Optional<EventoSimple> proximoEvento() {
    return repeticiones.stream()
        .map(EventoSimple::proximoEvento)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .min(EventoSimple::compararProximo);
  }

  @Override
  public boolean llegaATiempoDesde(Ubicacion ubicacion) {
    return proximoEvento()
        .map(it -> it.llegaATiempoDesde(ubicacion))
        .orElse(true);
  }

  @Override
  public Duration cuantoFalta() {
    return proximoEvento()
        .map(EventoSimple::cuantoFalta)
        .orElse(Iterables.getLast(repeticiones).cuantoFalta());
  }

  @Override
  public void enviarRecordatorios(Usuario owner) {
    repeticiones.forEach(it -> it.enviarRecordatorios(owner));
  }
}

