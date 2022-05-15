package models.factory;

import models.miembro.Tramo;
import models.mediodetransporte.BicicletaOPie;

import static models.factory.UbicacionFactory.medrano;
import static models.factory.UbicacionFactory.alem;

public class TramoFactory {
  public static Tramo tramoAPieDesdeMedranoHastaAlem(){
    return new Tramo(medrano(), alem(),new BicicletaOPie());
  }
}
