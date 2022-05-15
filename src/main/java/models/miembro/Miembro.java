package models.miembro;

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
    this.apellido = apellido;
    this.nombre = nombre;
    this.documento = documento;
    this.tipoDeDocumento = tipoDeDocumento;
    this.trayectos = new ArrayList<>(trayectos);
  }

  public void darDeAltaTrayecto(Trayecto trayecto) {
    this.trayectos.add(trayecto);
  }

  public List<Trayecto> getTrayectos() {
    return trayectos;
  }
}
