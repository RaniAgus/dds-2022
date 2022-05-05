package mia;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
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
    this.apellido = requireNonNull(apellido, "Un miembro debe tener un apellido.");
    this.nombre = requireNonNull(nombre, "Un miembro debe tener un nombre.");
    this.documento = requireNonNull(documento, "Un miembro debe tener un documento.");
    this.tipoDeDocumento = requireNonNull(tipoDeDocumento,
        "Un miembro debe tener un tipo de documento.");
    this.trayectos = new ArrayList<>(trayectos);
  }

  public void darDeAltaTrayecto(Trayecto trayecto) {
    this.trayectos.add(trayecto);
  }

  public List<Trayecto> getTrayectos() {
    return new ArrayList<>(trayectos);
  }
}
