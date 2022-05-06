package models.factory;

import models.Tramo;
import models.TramoEnBicicletaOPie;

import static models.factory.UbicacionFactory.medrano;
import static models.factory.UbicacionFactory.alem;

public class TramoFactory {
  public static Tramo tramoAPieDesdeMedranoHastaAlem(){
    return new TramoEnBicicletaOPie(medrano(), alem());
  }
}
