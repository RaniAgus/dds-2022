package models.factory;

import models.miembro.Miembro;
import models.miembro.TipoDeDocumento;

import static java.util.Collections.emptyList;

public class MiembroFactory {
  public static Miembro agus() {
    return new Miembro(
        "Agustin",
        "Ranieri",
        "0",
        TipoDeDocumento.DNI
    );
  }
}
