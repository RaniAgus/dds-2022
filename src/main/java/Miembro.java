import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class Miembro {
  private String nombre;
  private String apellido;
  private String documento;
  private TipoDeDocumento tipoDeDocumento;
  private List<Trayecto> trayectos;

  Miembro(String nombre, String apellido, String documento, TipoDeDocumento tipoDeDocumento) {
    this.apellido = requireNonNull(apellido, "Un miembro debe tener un apellido.");
    this.nombre = requireNonNull(nombre, "Un miembro debe tener un nombre.");
    this.documento = requireNonNull(documento, "Un miembro debe tener un documento.");
    this.tipoDeDocumento = requireNonNull(tipoDeDocumento,
        "Un miembro debe tener un tipo de documento.");
    this.trayectos = new ArrayList<Trayecto>();
  }

  public void darDeAltaTrayecto(Trayecto trayecto) {
    this.trayectos.add(trayecto);
  }

  public void vincularMiembroOrganizacion(Organizacion organizacion, Sector sector) {
    organizacion.aceptarVinculacionDeUnMiembro(this, sector);
  }
}
