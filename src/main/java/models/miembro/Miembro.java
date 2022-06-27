package models.miembro;

import java.time.LocalDate;

import models.da.Periodicidad;
import models.organizacion.Organizacion;

public class Miembro {
  private String nombre;
  private String apellido;
  private String documento;
  private TipoDeDocumento tipoDeDocumento;
  private Trayecto trayecto;

  public Miembro(String nombre,
                 String apellido,
                 String documento,
                 TipoDeDocumento tipoDeDocumento,
                 Trayecto trayecto) {
    this.apellido = apellido;
    this.nombre = nombre;
    this.documento = documento;
    this.tipoDeDocumento = tipoDeDocumento;
    this.trayecto = trayecto;
  }

  public Trayecto getTrayecto() {
    return trayecto;
  }

  public void setTrayecto(Trayecto trayecto) {
    this.trayecto = trayecto;
  }

  public Double huellaCarbonoPersonal(Periodicidad periodicidad) {
    return trayecto.carbonoEquivalente() * periodicidad.diasLaborales();
  }

  public Double impactoCarbonoEnOrganizacion(Organizacion organizacion, LocalDate fecha, Periodicidad periodicidad) {
    return huellaCarbonoPersonal(periodicidad)
          /organizacion.huellaCarbono(fecha, periodicidad);
  }
}
