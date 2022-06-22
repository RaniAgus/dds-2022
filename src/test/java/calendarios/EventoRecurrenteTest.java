package calendarios;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventoRecurrenteTest extends BaseTest {
  // 6. Permitir saber si dos eventos están solapado, y en tal caso, con qué otros eventos del calendario

  @Test
  void sePuedeSaberSiUnEventoRecurrenteEstaSolapadoConUnEventoSimple() {
    Evento recuperatorioSistemasDeGestion = crearEventoSimpleEnCampus("Recuperatorio de Gestión", LocalDateTime.now().plusDays(7), Duration.ofHours(1));
    Evento tpOperativos = crearEventoRecurrente(LocalDateTime.now().minusHours(1), Duration.ofHours(3), Duration.ofDays(7), LocalDateTime.now().plusMonths(1));

    assertTrue(recuperatorioSistemasDeGestion.estaSolapadoCon(tpOperativos));
    assertTrue(tpOperativos.estaSolapadoCon(recuperatorioSistemasDeGestion));
  }

  // 7. Permitir agendar eventos con repeticiones, con una frecuencia diaria, semanal, mensual o anual

  @Test
  void sePuedenAgendarYListarEventosRecurrentes() {
    Usuario usuario = crearUsuario("rene@gugle.com.ar");
    Calendario calendario = crearCalendarioVacio();
    usuario.agregarCalendario(calendario);

    Evento unRecurrente = crearEventoRecurrente(
        LocalDateTime.of(2020, 9, 1, 19, 0), Duration.ofMinutes(45),
        Duration.ofDays(7), LocalDateTime.of(2020, 11, 1, 19, 0));
    calendario.agendar(unRecurrente);

    List<EventoSimple> eventos = usuario.eventosEntreFechas(
        LocalDateTime.of(2020, 9, 14, 9, 0),
        LocalDateTime.of(2020, 9, 28, 21, 0));

    assertEquals(eventos.size(), 2);
  }

  @Test
  void unEventoRecurrenteSabeCuantoFaltaParaSuProximaRepeticion() {
    Evento unRecurrente = crearEventoRecurrente(
        LocalDateTime.now().minusHours(1), Duration.ofHours(1),
        Duration.ofDays(15), LocalDateTime.now().plusMonths(3));

    assertTrue(unRecurrente.cuantoFalta().compareTo(Duration.of(15, ChronoUnit.DAYS)) <= 0);
    assertTrue(unRecurrente.cuantoFalta().compareTo(Duration.of(14, ChronoUnit.DAYS)) >= 0);
  }
}
