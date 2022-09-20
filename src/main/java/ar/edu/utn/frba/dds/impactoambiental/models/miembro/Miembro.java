package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodo;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.stream.Collectors;
@Entity
public class Miembro extends EntidadPersistente {
  private String nombre;
  private String apellido;
  private String documento;
  @Enumerated(EnumType.STRING)
  private TipoDeDocumento tipoDeDocumento;
  @ManyToMany
  private List<Trayecto> trayectos;

  public Miembro() {
  }

  public Miembro(String nombre,
                 String apellido,
                 String documento,
                 TipoDeDocumento tipoDeDocumento,
                 List<Trayecto> trayectos) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.documento = documento;
    this.tipoDeDocumento = tipoDeDocumento;
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
