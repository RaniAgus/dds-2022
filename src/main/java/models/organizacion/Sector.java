package models.organizacion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import models.da.Periodicidad;
import models.miembro.Trayecto;

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

  public Double huellaCarbono(Periodicidad periodicidad) {
    return getVinculacionesSegunEstado(EstadoVinculo.ACEPTADO).stream()
        .map(Vinculacion::getMiembro)
        .flatMap(it -> it.getTrayectos().stream())
        .distinct()
        .mapToDouble(Trayecto::carbonoEquivalente)
        .sum() * periodicidad.diasLaborales();
  }

  public Double huellaCarbonoPorMiembro(Periodicidad periodicidad){
    return huellaCarbono(periodicidad) / getVinculacionesSegunEstado(EstadoVinculo.ACEPTADO).size();
  }
}
