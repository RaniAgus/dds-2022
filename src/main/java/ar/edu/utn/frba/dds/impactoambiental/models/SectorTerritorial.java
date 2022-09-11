package ar.edu.utn.frba.dds.impactoambiental.models;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;

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

  public Double huellaCarbono(Periodo periodo) {
    return organizaciones.stream()
          .mapToDouble(o -> o.huellaCarbono(periodo))
          .sum();
  }
}
