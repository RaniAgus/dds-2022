package mia.factory;

import mia.Linea;
import mia.TipoDeTransportePublico;

import static java.util.Arrays.asList;
import static mia.factory.UbicacionFactory.alem;
import static mia.factory.UbicacionFactory.medrano;

public class LineaFactory {
  public static Linea subteB() {
    return new Linea("Subte B", asList(medrano(), alem()), TipoDeTransportePublico.SUBTE);
  }
}
