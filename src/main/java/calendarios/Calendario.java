package calendarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Calendario {
  private List<Evento> eventos = new ArrayList<>();

  public void agendar(Evento evento) {
    eventos.add(evento);
  }

  public boolean estaAgendado(Evento evento) {
    return eventos.contains(evento);
  }

  public List<Evento> eventosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return getEventos(it -> it.estaEntreFechas(inicio, fin));
  }

  public List<Evento> eventosSolapadosCon(Evento evento) {
    return getEventos(it -> it.estaSolapadoCon(evento));
  }

  public Optional<Evento> proximoEvento() {
    return eventos.stream()
        .filter(it -> !it.cuantoFalta().isNegative())
        .sorted()
        .findFirst();
  }

  private List<Evento> getEventos(Predicate<Evento> filtro) {
    return eventos.stream()
        .filter(filtro)
        .collect(Collectors.toList());
  }
}
