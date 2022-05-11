package models.factory;

import models.Miembro;
import models.Sector;
import models.Vinculacion;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

public class SectorFactory {
  public static Sector unSectorVacio() {
    return new Sector(emptyList());
  }

  public static Sector unSectorConSolicitudes(List<Vinculacion> vinculaciones) {
    return new Sector(vinculaciones);
  }
}
