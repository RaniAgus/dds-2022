package mia.factory;

import mia.Miembro;
import mia.Sector;

import java.util.List;

import static java.util.Collections.emptyList;

public class SectorFactory {
  public static Sector unSectorVacio() {
    return new Sector(emptyList(), emptyList());
  }

  public static Sector unSectorConSolicitudes(List<Miembro> miembrosPendientes) {
    return new Sector(emptyList(), miembrosPendientes);
  }
}
