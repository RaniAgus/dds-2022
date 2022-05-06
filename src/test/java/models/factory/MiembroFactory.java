package models.factory;

import models.Miembro;
import models.TipoDeDocumento;

import static java.util.Collections.emptyList;

public class MiembroFactory {
  public static Miembro agus() {
    return new Miembro(
        "Agustin",
        "Ranieri",
        "0",
        TipoDeDocumento.DNI,
        emptyList()
    );
  }
}
