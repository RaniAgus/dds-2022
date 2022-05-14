package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Sector {
  private Set<Vinculacion> vinculaciones;

  public Sector(List<Vinculacion> vinculaciones) {
    this.vinculaciones = new HashSet<>(vinculaciones);
  }

  public void solicitarVinculacion(Vinculacion vinculacion) {
    this.vinculaciones.add(vinculacion);
  }

  public List<Vinculacion> getVinculacionesSegunEstado(EstadoVinculo estado) {
    return this.vinculaciones.stream()
        .filter(vinculacion -> vinculacion.getEstado() == estado)
        .collect(Collectors.toList());
  }
}
