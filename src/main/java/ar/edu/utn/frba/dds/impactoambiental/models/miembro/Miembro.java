package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.da.Periodicidad;
import ar.edu.utn.frba.dds.impactoambiental.models.organizacion.Organizacion;

import java.time.LocalDate;
import java.util.List;

public class Miembro {
  private String nombre;
  private String apellido;
  private String documento;
  private TipoDeDocumento tipoDeDocumento;
  private List<Trayecto> trayectos;

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

  public Double huellaCarbonoPersonal(Periodicidad periodicidad) {
    return getTrayectos().stream()
      .mapToDouble(Trayecto::carbonoEquivalente)
      .sum() * periodicidad.diasLaborales();
  }

  public Double impactoCarbonoEnOrganizacion(Organizacion organizacion, LocalDate fecha, Periodicidad periodicidad) {
    return huellaCarbonoPersonal(periodicidad)
          /organizacion.huellaCarbono(fecha, periodicidad);
  }

  public void darDeAltaTrayecto(Trayecto trayecto) {
    trayectos.add(trayecto);
  }
}
