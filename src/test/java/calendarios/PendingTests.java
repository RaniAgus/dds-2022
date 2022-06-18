package calendarios;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PendingTests extends CalendariosTest {
  // 7. Permitir agendar eventos con repeticiones, con una frecuencia diaria, semanal, mensual o anual

  @Test
  void sePuedenAgendarYListarEventosRecurrentes() {
    Usuario usuario = crearUsuario("rene@gugle.com.ar");

    // TODO completar
    fail("Agregar uno evento recurrente que se repita los martes a las 19 y dure 45 minutos y " +
        "por tanto deberá aparecer dos veces entre el lunes 14 a las 9 y el lunes 28 a las 21");

    List<Evento> eventos = usuario.eventosEntreFechas(
        LocalDateTime.of(2020, 9, 14, 9, 0),
        LocalDateTime.of(2020, 9, 28, 21, 0));

    assertEquals(eventos.size(), 2);
  }

  @Test
  void unEventoRecurrenteSabeCuantoFaltaParaSuProximaRepeticion() {
    // TODO completar
    Evento unRecurrente = fail("crear un evento recurrente que se repita, a partir de hoy, cada 15 días, y arranque una hora antes de la hora actual");

    assertTrue(unRecurrente.cuantoFalta().compareTo(Duration.of(15, ChronoUnit.DAYS)) <= 0);
    assertTrue(unRecurrente.cuantoFalta().compareTo(Duration.of(14, ChronoUnit.DAYS)) >= 0);
  }

  // 9. Permitir asignarle a un evento varios recordatorios, que se enviarán cuando falte un cierto tiempo

}
