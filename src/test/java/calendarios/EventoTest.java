package calendarios;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventoTest extends CalendariosTest {
  // 3. Permitir que los eventos registren nombre, fecha y hora de inicio y fin, ubicación, invitades (otros usuaries)

  @Test
  void unEventoPuedeTenerMultiplesInvitades() {
    Usuario romulo = crearUsuario("romulo@gugle.com.ar");
    Usuario remo = crearUsuario("remo@gugle.com.ar");

    Evento seguimientoDeTP = crearEventoSimpleConInvitados(LocalDateTime.of(2021, 10, 1, 15, 30), Duration.of(30, MINUTES), asList(romulo, remo));

    assertTrue(seguimientoDeTP.tieneInvitado(romulo));
    assertTrue(seguimientoDeTP.tieneInvitado(remo));
  }

  // 5. Permitir saber cuánto falta para un cierto calendarios.evento (por ejemplo, 15 horas)

  @Test
  void unEventoSabeCuantoFalta() {
    LocalDateTime inicio = LocalDateTime.now().plusDays(60);
    Evento parcialDds = crearEventoSimpleEnMedrano("Parcial DDS", inicio, Duration.of(2,  HOURS));

    assertTrue(parcialDds.cuantoFalta().compareTo(Duration.of(60, ChronoUnit.DAYS)) <= 0);
    assertTrue(parcialDds.cuantoFalta().compareTo(Duration.of(59, ChronoUnit.DAYS)) >= 0);
  }

  // 6. Permitir saber si dos eventos están solapado, y en tal caso, con qué otros eventos del calendario

  @Test
  void sePuedeSaberSiUnEventoEstaSolapadoCuandoEstaParcialmenteIncluido() {
    Evento recuperatorioSistemasDeGestion = crearEventoSimpleEnMedrano("Recuperatorio Sistemas de Gestion", LocalDateTime.of(2021, 6, 19, 9, 0), Duration.of(2, HOURS));
    Evento tpOperativos = crearEventoSimpleEnMedrano("Entrega de Operativos", LocalDateTime.of(2021, 6, 19, 10, 0), Duration.of(2, HOURS));

    assertTrue(recuperatorioSistemasDeGestion.estaSolapadoCon(tpOperativos));
    assertTrue(tpOperativos.estaSolapadoCon(recuperatorioSistemasDeGestion));
  }

  @Test
  void sePuedeSaberSiUnEventoEstaSolapadoCuandoEstaTotalmenteIncluido() {
    Evento recuperatorioSistemasDeGestion = crearEventoSimpleEnMedrano("Recuperatorio Sistemas de Gestion", LocalDateTime.of(2021, 6, 19, 9, 0), Duration.of(4, HOURS));
    Evento tpOperativos = crearEventoSimpleEnMedrano("Entrega de Operativos", LocalDateTime.of(2021, 6, 19, 10, 0), Duration.of(2, HOURS));

    assertTrue(recuperatorioSistemasDeGestion.estaSolapadoCon(tpOperativos));
    assertTrue(tpOperativos.estaSolapadoCon(recuperatorioSistemasDeGestion));
  }


  @Test
  void sePuedeSaberSiUnEventoEstaSolapadoCuandoNoEstaSolapado() {
    Evento recuperatorioSistemasDeGestion = crearEventoSimpleEnMedrano("Recuperatorio Sistemas de Gestion", LocalDateTime.of(2021, 6, 19, 9, 0), Duration.of(3, HOURS));
    Evento tpOperativos = crearEventoSimpleEnMedrano("Entrega de Operativos", LocalDateTime.of(2021, 6, 19, 18, 0), Duration.of(2, HOURS));

    assertFalse(recuperatorioSistemasDeGestion.estaSolapadoCon(tpOperativos));
    assertFalse(tpOperativos.estaSolapadoCon(recuperatorioSistemasDeGestion));
  }
}
