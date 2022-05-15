package models.factory;

import models.organizacion.Sector;
import models.organizacion.Vinculacion;

import java.util.List;

import static java.util.Collections.emptyList;

public class SectorFactory {
  public static Sector unSectorVacio() {
    return new Sector(emptyList());
  }

  public static Sector unSectorConSolicitudes(List<Vinculacion> vinculaciones) {
    return new Sector(vinculaciones);
  }
}
