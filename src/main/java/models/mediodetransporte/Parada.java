package models.mediodetransporte;

import models.geolocalizacion.Distancia;
import models.geolocalizacion.Ubicacion;

public class Parada {
  private String nombre;
  private Distancia distanciaAProximaParada;
  private Distancia distanciaAAnteriorParada;

  public Parada(Distancia distanciaAProximaParada, Distancia distanciaAAnteriorParada, String nombre) {
    this.distanciaAProximaParada = distanciaAProximaParada;
    this.distanciaAAnteriorParada=distanciaAAnteriorParada;
    this.nombre = nombre;
  }

  public Distancia getDistanciaAProximaParada() {
    return distanciaAProximaParada;
  }

  public Distancia getDistanciaAAnteriorParada() {
    return distanciaAAnteriorParada;
  }

  public void setDistanciaAProximaParada(Distancia distanciaAProximaParada) {
    this.distanciaAProximaParada = distanciaAProximaParada;
  }
  public void setDistanciaAAnteriorParada(Distancia distanciaAAnteriorParada) {
    this.distanciaAAnteriorParada = distanciaAProximaParada;
  }
}
