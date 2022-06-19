package calendarios;

import calendarios.servicios.GugleMapas;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Evento implements Comparable<Evento> {
  private GugleMapas gugleMapas;
  private String nombre;
  private Horario horario;
  private Ubicacion ubicacion;
  private List<Usuario> invitados;

  public Evento(GugleMapas gugleMapas,
                String nombre,
                Horario horario,
                Ubicacion ubicacion,
                List<Usuario> invitados) {
    this.gugleMapas = gugleMapas;
    this.nombre = nombre;
    this.horario = horario;
    this.ubicacion = ubicacion;
    this.invitados = invitados;
  }

  public boolean tieneInvitado(Usuario usuario) {
    return invitados.contains(usuario);
  }

  public boolean estaEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return horario.estaEntreFechas(inicio, fin);
  }

  public boolean estaSolapadoCon(Evento otro) {
    return horario.estaSolapadoCon(otro.getHorario());
  }

  public Duration cuantoFalta() {
    return horario.cuantoFalta();
  }

  public boolean llegaATiempoDesde(Ubicacion ubicacion) {
    return gugleMapas.tiempoEstimadoHasta(ubicacion, getUbicacion()).compareTo(cuantoFalta()) <= 0;
  }

  private Ubicacion getUbicacion() {
    return ubicacion;
  }

  public Horario getHorario() {
    return horario;
  }

  @Override
  public int compareTo(Evento otro) {
    return cuantoFalta().compareTo(otro.cuantoFalta());
  }

  @Override
  public String toString() {
    return "Evento{" +
        "nombre='" + nombre + '\'' +
        ", horario=" + horario +
        ", ubicacion=" + ubicacion +
        '}';
  }
}
