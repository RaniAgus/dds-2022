package calendarios;

import calendarios.servicios.PositionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Usuario {
  private PositionService positionService;
  private String email;
  private List<Calendario> calendarios = new ArrayList<>();

  public Usuario(PositionService positionService, String email) {
    this.positionService = positionService;
    this.email = email;
  }

  public Ubicacion getUbicacion() {
    return positionService.ubicacionActual(getEmail());
  }

  public String getEmail() {
    return email;
  }

  public void agregarCalendario(Calendario calendario) {
    calendarios.add(calendario);
  }

  public boolean tieneCalendario(Calendario calendario) {
    return calendarios.contains(calendario);
  }

  public List<Evento> eventosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return calendarios.stream()
        .flatMap(it -> it.eventosEntreFechas(inicio, fin).stream())
        .collect(Collectors.toList());
  }

  public boolean llegaATiempoAlProximoEvento()  {
    return proximoEvento()
        .map(it -> it.llegaATiempoDesde(getUbicacion()))
        .orElse(true);
  }

  private Optional<Evento> proximoEvento() {
    return calendarios.stream()
        .flatMap(it -> Stream.of(it.proximoEvento()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .sorted()
        .findFirst();
  }
}
