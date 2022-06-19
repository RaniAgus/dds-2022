package calendarios;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class Evento implements Comparable<EventoSimple> {
  public abstract boolean estaEntreFechas(LocalDateTime inicio, LocalDateTime fin);
  public abstract boolean estaSolapadoCon(Evento otro);
  public abstract Stream<EventoSimple> eventosEntreFechas(LocalDateTime inicio, LocalDateTime fin);
  public abstract Stream<EventoSimple> eventosSolapadosCon(Evento otro);
  public abstract Optional<EventoSimple> proximoEvento();
  public abstract boolean llegaATiempoDesde(Ubicacion ubicacion);
  public abstract Duration cuantoFalta();

  @Override
  public int compareTo(EventoSimple otro) {
    return cuantoFalta().compareTo(otro.cuantoFalta());
  }
}
