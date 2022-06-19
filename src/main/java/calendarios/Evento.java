package calendarios;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

public interface Evento {
  boolean estaEntreFechas(LocalDateTime inicio, LocalDateTime fin);
  boolean estaSolapadoCon(Evento otro);
  Stream<EventoSimple> eventosEntreFechas(LocalDateTime inicio, LocalDateTime fin);
  Stream<EventoSimple> eventosSolapadosCon(Evento otro);
  Optional<EventoSimple> proximoEvento();
  boolean llegaATiempoDesde(Ubicacion ubicacion);
  Duration cuantoFalta();
}
