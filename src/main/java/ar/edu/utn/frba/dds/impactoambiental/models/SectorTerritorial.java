package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;

import java.time.LocalDate;
import java.util.List;

public class SectorTerritorial {
  private String nombre;
  private List<Organizacion> organizaciones;

  public SectorTerritorial(String nombre, List<Organizacion> organizaciones) {
    this.nombre = nombre;
    this.organizaciones = organizaciones;
  }

  public void agregarOrganizacion(Organizacion organizacion) {
    organizaciones.add(organizacion);
  }

  public Double huellaCarbono(LocalDate fecha, Periodicidad periodicidad) {
    return organizaciones.stream()
          .mapToDouble(o -> o.huellaCarbono(fecha, periodicidad))
          .sum();
  }
}
