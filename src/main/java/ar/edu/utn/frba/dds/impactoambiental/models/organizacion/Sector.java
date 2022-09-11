package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Trayecto;

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
