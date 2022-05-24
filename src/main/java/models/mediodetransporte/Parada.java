package models.mediodetransporte;

import models.geolocalizacion.Distancia;
import models.geolocalizacion.Ubicacion;

public class Parada {
  public Distancia distanciaAProximaParada;

  Parada(Distancia distanciaAProximaParada){
    this.distanciaAProximaParada = distanciaAProximaParada;
  }

  public Distancia getDistanciaAProximaParada() {
    return distanciaAProximaParada;
  }
}
