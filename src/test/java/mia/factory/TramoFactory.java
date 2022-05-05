package mia.factory;

import mia.Tramo;
import mia.TramoEnBicicletaOPie;

import static mia.factory.UbicacionFactory.medrano;
import static mia.factory.UbicacionFactory.alem;

public class TramoFactory {
  public static Tramo tramoAPieDesdeMedranoHastaAlem(){
    return new TramoEnBicicletaOPie(medrano(), alem());
  }
}
