package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Miembro extends EntidadPersistente {
  @ManyToMany
  private List<Trayecto> trayectos;

  public Miembro() {
  }

  public Miembro(List<Trayecto> trayectos) {
    this.trayectos = trayectos;
  }

  public List<Trayecto> getTrayectos() {
    return trayectos;
  }

  public List<Trayecto> getTrayectosEnPeriodo(Periodo periodo) {
    return getTrayectos().stream()
        .filter(trayecto -> trayecto.estaEnPeriodo(periodo))
        .collect(Collectors.toList());
  }

  public Double huellaCarbonoPersonal(Periodo periodo) {
    return getTrayectosEnPeriodo(periodo).stream()
        .mapToDouble(Trayecto::carbonoEquivalente)
        .sum();
  }

  public Double impactoCarbonoEnOrganizacion(Organizacion organizacion, Periodo periodo) {
    return huellaCarbonoPersonal(periodo) / organizacion.huellaCarbono(periodo);
  }

  public void darDeAltaTrayecto(Trayecto trayecto) {
    trayectos.add(trayecto);
  }
}
