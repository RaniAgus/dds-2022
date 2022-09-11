package ar.edu.utn.frba.dds.impactoambiental.models.organizacion;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.miembro.Miembro;
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

  public List<Vinculacion> getVinculacionesPendientes() {
    return this.vinculaciones.stream()
        .filter(vinculacion -> vinculacion.getEstado() == EstadoVinculo.PENDIENTE)
        .collect(Collectors.toList());
  }

  public List<Miembro> getMiembros() {
    return this.vinculaciones.stream()
        .filter(vinculacion -> vinculacion.getEstado() == EstadoVinculo.ACEPTADO)
        .map(Vinculacion::getMiembro)
        .collect(Collectors.toList());
  }

  public List<Trayecto> getTrayectosEnPeriodo(Periodo periodo) {
    return getMiembros().stream()
        .flatMap(miembro -> miembro.getTrayectosEnPeriodo(periodo).stream())
        .distinct()
        .collect(Collectors.toList());
  }

  public Double huellaCarbono(Periodo periodo) {
    return getTrayectosEnPeriodo(periodo).stream()
        .mapToDouble(Trayecto::carbonoEquivalente)
        .sum();
  }

  public Double huellaCarbonoPorMiembro(Periodo periodo) {
    return huellaCarbono(periodo) / getMiembros().size();
  }
}
