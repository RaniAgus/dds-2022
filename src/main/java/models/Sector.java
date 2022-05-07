package models;

import java.util.ArrayList;
import java.util.List;


public class Sector {
  private List<Miembro> miembrosPendientes;
  private List<Miembro> miembros;

  public Sector(List<Miembro> miembros, List<Miembro> miembrosPendientes) {
    this.miembrosPendientes = new ArrayList<>(miembrosPendientes);
    this.miembros = new ArrayList<>(miembros);
  }

  public void solicitarVinculacion(Miembro miembro) {
    this.miembrosPendientes.add(miembro);
  }

  public void vincularMiembro(Miembro miembro) {
    if (!this.miembrosPendientes.remove(miembro)) {
      throw new IllegalArgumentException("El miembro no solicitó vincularse al sector");
    }
    this.miembros.add(miembro);
  }

  public List<Miembro> getMiembros() {
    return miembros;
  }

  public List<Miembro> getMiembrosPendientes() {
    return miembrosPendientes;
  }
}
