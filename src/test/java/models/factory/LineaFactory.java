package models.factory;

import models.geolocalizacion.Distancia;
import models.geolocalizacion.Unidad;
import models.mediodetransporte.Linea;
import models.mediodetransporte.Parada;
import models.mediodetransporte.TipoDeTransportePublico;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static models.factory.UbicacionFactory.alem;
import static models.factory.UbicacionFactory.medrano;

public class LineaFactory {
  public static Linea subteB() {
    return new Linea("Subte B", asList(new Parada(new Distancia(new BigDecimal(35.8), Unidad.KM),"medrano"), new Parada(new Distancia(new BigDecimal(35.8), Unidad.KM),"alem")), TipoDeTransportePublico.SUBTE);
  }
}
