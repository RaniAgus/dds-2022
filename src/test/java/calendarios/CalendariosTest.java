package calendarios;

import calendarios.servicios.GugleMapas;
import calendarios.servicios.PositionService;
import org.junit.jupiter.api.BeforeEach;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

abstract class CalendariosTest {

  protected PositionService positionService;
  protected GugleMapas gugleMapas;

  protected static Ubicacion utnMedrano = new Ubicacion(-34.5984145, -58.4222096);
  protected static Ubicacion utnCampus = new Ubicacion(-34.6591644,-58.4694862);

  @BeforeEach
  void initFileSystem() {
    positionService = mock(PositionService.class);
    gugleMapas = mock(GugleMapas.class);
  }

  /**
   * @return une usuarie con el mail dado
   */
  protected Usuario crearUsuario(String email) {
    return new Usuario(positionService, email);
  }
  /*
   * @return Un calendario sin ningún evento agendado aún
   */

  protected Calendario crearCalendarioVacio() {
    return new Calendario();
  }

  protected EventoSimple crearEventoSimpleEnMedrano(String nombre, LocalDateTime inicio, Duration duracion) {
    return crearEventoSimple(nombre, inicio, inicio.plus(duracion), utnMedrano, Collections.emptyList());
  }

  protected EventoSimple crearEventoSimpleEnCampus(String nombre, LocalDateTime inicio, Duration duracion) {
    return crearEventoSimple(nombre, inicio, inicio.plus(duracion), utnCampus, Collections.emptyList());
  }

  protected EventoSimple crearEventoSimpleConInvitados(LocalDateTime inicio, Duration duracion, List<Usuario> invitados) {
    return crearEventoSimple("Seguimiento de TPA", inicio, inicio.plus(duracion), utnCampus, invitados);
  }

  protected EventoRecurrente crearEventoRecurrenteEnCampus(LocalDateTime inicio, Duration duracion, Duration frecuencia, LocalDateTime limite) {
    List<EventoSimple> repeticiones = new ArrayList<>();
    Horario horario = new Horario(inicio, inicio.plus(duracion));
    while (horario.estaAntesDe(limite)) {
      repeticiones.add(new EventoSimple(gugleMapas, "Seguimiento de TPA", horario, utnCampus, Collections.emptyList()));
      horario = horario.sumar(frecuencia);
    }
    return new EventoRecurrente(repeticiones);
  }

  /**
   * @return un evento sin invtades que no se repite, que tenga el nombre, fecha de inicio y fin, ubicación dados
   */
  protected EventoSimple crearEventoSimple(String nombre, LocalDateTime inicio, LocalDateTime fin, Ubicacion ubicacion, List<Usuario> invitados) {
    return new EventoSimple(gugleMapas, nombre, new Horario(inicio, fin), ubicacion, invitados);
  }
}
