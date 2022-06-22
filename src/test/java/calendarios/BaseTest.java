package calendarios;

import calendarios.servicios.GugleMapas;
import calendarios.servicios.PositionService;
import calendarios.servicios.ShemailLib;
import org.junit.jupiter.api.BeforeEach;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

abstract class BaseTest {

  protected PositionService positionService;
  protected GugleMapas gugleMapas;
  protected ShemailLib shemailLib;

  protected static Ubicacion utnMedrano = new Ubicacion(-34.5984145, -58.4222096);
  protected static Ubicacion utnCampus = new Ubicacion(-34.6591644,-58.4694862);

  @BeforeEach
  void initFileSystem() {
    positionService = mock(PositionService.class);
    gugleMapas = mock(GugleMapas.class);
    shemailLib = mock(ShemailLib.class);
  }

  /**
   * @return une usuarie con el mail dado
   */
  protected Usuario crearUsuario(String email) {
    return new Usuario(positionService, email);
  }

  /**
   * @return Un calendario sin ningún evento agendado aún
   */
  protected Calendario crearCalendarioVacio() {
    return new Calendario();
  }

  /**
   * @return Un evento simple con ubicación en utnMedrano
   */
  protected EventoSimple crearEventoSimpleEnMedrano(String nombre, LocalDateTime inicio, Duration duracion) {
    return crearEventoSimple(nombre, inicio, inicio.plus(duracion), utnMedrano, Collections.emptyList(), Collections.emptyList());
  }

  /**
   * @return Un evento simple con ubicación en utnCampus
   */
  protected EventoSimple crearEventoSimpleEnCampus(String nombre, LocalDateTime inicio, Duration duracion) {
    return crearEventoSimple(nombre, inicio, inicio.plus(duracion), utnCampus, Collections.emptyList(), Collections.emptyList());
  }

  /**
   * @return Un evento simple con los invitados dados
   */
  protected EventoSimple crearEventoSimpleConInvitados(LocalDateTime inicio, Duration duracion, List<Usuario> invitados) {
    return crearEventoSimple("Seguimiento de TPA", inicio, inicio.plus(duracion), utnCampus, Collections.emptyList(), invitados);
  }

  /**
   * @return Un evento simple con los recordatorios e invitados dados
   */
  protected EventoSimple crearEventoSimpleConRecordatorios(LocalDateTime inicio, List<Recordatorio> recordatorios, List<Usuario> invitados) {
    return crearEventoSimple("Entrega Operativos", inicio, inicio.plusHours(2), utnMedrano, recordatorios, invitados);
  }

  /**
   * @return un evento sin invtades que no se repite, que tenga el nombre, fecha de inicio y fin, ubicación dados
   */
  private EventoSimple crearEventoSimple(String nombre, LocalDateTime inicio, LocalDateTime fin, Ubicacion ubicacion, List<Recordatorio> recordatorios, List<Usuario> invitados) {
    return new EventoSimple(gugleMapas, nombre, new Horario(inicio, fin), ubicacion, recordatorios, invitados);
  }

  /**
   * @return Un evento sin invitados que se repite
   */
  protected EventoRecurrente crearEventoRecurrente(LocalDateTime inicio, Duration duracion, Duration frecuencia, LocalDateTime limite) {
    List<EventoSimple> repeticiones = new ArrayList<>();
    Horario horario = new Horario(inicio, inicio.plus(duracion));
    while (horario.estaAntesDe(limite)) {
      repeticiones.add(new EventoSimple(gugleMapas, "Seguimiento de TPA", horario, utnCampus, Collections.emptyList(), Collections.emptyList()));
      horario = horario.sumar(frecuencia);
    }
    return new EventoRecurrente(repeticiones);
  }

  /**
   * @return un recordatorio que deberá ser enviado cuando al evento le falte la duración dada
   */
  protected Recordatorio crearRecordatorio(Duration duracion) {
    return new Recordatorio(shemailLib, duracion);
  }

}
