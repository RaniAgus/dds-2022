package models.miembro;

import models.geolocalizacion.Distancia;
import models.mediodetransporte.Linea;
import models.mediodetransporte.Parada;

public class TramoEnTransportePublico implements Tramo {

  private Parada paradaInicial;
  private Parada paradaFinal;
  private Linea linea;

  TramoEnTransportePublico(Parada paradaFinal, Parada paradaInicial, Linea linea) {
    this.linea=linea;
    this.paradaFinal=paradaFinal;
    this.paradaInicial=paradaInicial;
  }

  @Override
  public Distancia getDistancia() {
    return linea.distanciaEntreParadas(paradaInicial,paradaFinal);
  }

  @Override
  public boolean esCompartible() {
    return false;
  }
}
