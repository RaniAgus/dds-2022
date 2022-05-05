import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;


public class Sector {
  Organizacion organizacion;
  List<Miembro> miembros;

  public Sector(Organizacion organizacion) {
    this.organizacion = requireNonNull(organizacion,
        "Un sector debe estar asociado a una organizacion.");
    this.miembros = new ArrayList<>();
  }

  public void vincularMiembro(Miembro miembro) {
    this.miembros.add(requireNonNull(miembro, "Se debe vincular un miembro valido."));
  }

  public List<Miembro> getMiembros() {
    return miembros;
  }
}
