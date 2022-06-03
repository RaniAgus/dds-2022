package models.mediodetransporte;

import models.geolocalizacion.Distancia;
import models.geolocalizacion.Ubicacion;

public class Parada {
  public String nombre;
  public Distancia distanciaAProximaParada;

  public Parada(Distancia distanciaAProximaParada, String nombre) {
    this.distanciaAProximaParada = distanciaAProximaParada;
    this.nombre = nombre;
  }

  public Distancia getDistanciaAProximaParada() {
    return distanciaAProximaParada;
  }

  public void setDistanciaAProximaParada(Distancia distanciaAProximaParada) {
    this.distanciaAProximaParada = distanciaAProximaParada;
  }
}
