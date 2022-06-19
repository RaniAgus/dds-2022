package calendarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Calendario {
  private List<Evento> eventos = new ArrayList<>();

  private List<Evento> getEventos() {
    return eventos;
  }

  public void agendar(Evento evento) {
    getEventos().add(evento);
  }

  public boolean estaAgendado(Evento evento) {
    return getEventos().contains(evento);
  }

  public List<EventoSimple> eventosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return getEventos().stream()
        .flatMap(it -> it.eventosEntreFechas(inicio, fin))
        .collect(Collectors.toList());
  }

  public List<EventoSimple> eventosSolapadosCon(Evento evento) {
    return getEventos().stream()
        .flatMap(it -> it.eventosSolapadosCon(evento))
        .collect(Collectors.toList());
  }

  public Optional<EventoSimple> proximoEvento() {
    return getEventos().stream()
        .map(Evento::proximoEvento)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .min(EventoSimple::compararProximo);
  }

  public void enviarRecordatorios(Usuario owner) {
    getEventos().forEach(it -> it.enviarRecordatorios(owner));
  }
}
