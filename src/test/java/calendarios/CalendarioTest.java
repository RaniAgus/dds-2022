package calendarios;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalendarioTest extends CalendariosTest {
  // 2. Permitir que en cada calendario se agenden múltiples eventos

  @Test
  void unCalendarioPermiteAgendarUnEvento() {
    Calendario calendario = crearCalendarioVacio();

    Evento seguimientoDeTP = crearEventoSimpleEnMedrano("Seguimiento de TP", LocalDateTime.of(2021, 10, 1, 15, 30), Duration.of(30, MINUTES));
    calendario.agendar(seguimientoDeTP);

    assertTrue(calendario.estaAgendado(seguimientoDeTP));
  }

  @Test
  void unCalendarioPermiteAgendarDosEventos() {
    Calendario calendario = crearCalendarioVacio();
    LocalDateTime inicio = LocalDateTime.of(2021, 10, 1, 15, 30);

    Evento seguimientoDeTPA = crearEventoSimpleEnMedrano("Seguimiento de TPA", inicio, Duration.of(30, MINUTES));
    Evento practicaParcial = crearEventoSimpleEnMedrano("Practica para el primer parcial", inicio.plusMinutes(60), Duration.of(90, MINUTES));

    calendario.agendar(seguimientoDeTPA);
    calendario.agendar(practicaParcial);

    assertTrue(calendario.estaAgendado(seguimientoDeTPA));
    assertTrue(calendario.estaAgendado(practicaParcial));
  }

  // 4. Permitir listar los próximos eventos entre dos fechas

  @Test
  void sePuedeListarUnEventoEntreDosFechasParaUnCalendario() {
    Calendario calendario = crearCalendarioVacio();
    Evento tpRedes = crearEventoSimpleEnMedrano("TP de Redes", LocalDateTime.of(2020, 4, 3, 16, 0), Duration.of(2, HOURS));

    calendario.agendar(tpRedes);

    List<Evento> eventos = calendario.eventosEntreFechas(
        LocalDate.of(2020, 4, 1).atStartOfDay(),
        LocalDate.of(2020, 4, 4).atStartOfDay());

    assertEquals(singletonList(tpRedes), eventos);
  }

  // 6. Permitir saber si dos eventos están solapado, y en tal caso, con qué otros eventos del calendario

  @Test
  void sePuedeSaberConQueEventosEstaSolapado() {
    Evento recuperatorioSistemasDeGestion = crearEventoSimpleEnMedrano("Recuperatorio Sistemas de Gestion", LocalDateTime.of(2021, 6, 19, 9, 0), Duration.of(2, HOURS));
    Evento tpOperativos = crearEventoSimpleEnMedrano("Entrega de Operativos", LocalDateTime.of(2021, 6, 19, 10, 0), Duration.of(2, HOURS));
    Evento tramiteEnElBanco = crearEventoSimpleEnMedrano("Tramite en el banco", LocalDateTime.of(2021, 6, 19, 9, 0), Duration.of(4, HOURS));

    Calendario calendario = crearCalendarioVacio();

    calendario.agendar(recuperatorioSistemasDeGestion);
    calendario.agendar(tpOperativos);

    assertEquals(asList(recuperatorioSistemasDeGestion, tpOperativos), calendario.eventosSolapadosCon(tramiteEnElBanco));
  }

  // 8. Permitir saber si le usuarie llega al evento más próximo a tiempo, tomando en cuenta la ubicación actual de le usuarie y destino.

  @Test
  void sePuedeSaberCualEsElProximoEventoDeUnCalendario() {
    Calendario calendario = crearCalendarioVacio();

    Evento tpRedes = crearEventoSimpleEnMedrano("TP de Redes", LocalDateTime.now().minusHours(1), Duration.of(1, HOURS));
    Evento tpDeGestion = crearEventoSimpleEnMedrano("TP de Gestión", LocalDateTime.now().plusMinutes(30), Duration.of(30, MINUTES));
    Evento tpDeDds = crearEventoSimpleEnMedrano("TP de DDS", LocalDateTime.now().plusHours(1), Duration.of(1, HOURS));

    calendario.agendar(tpDeDds);
    calendario.agendar(tpRedes);
    calendario.agendar(tpDeGestion);

    assertEquals(calendario.proximoEvento().get(), tpDeGestion);
  }
}
