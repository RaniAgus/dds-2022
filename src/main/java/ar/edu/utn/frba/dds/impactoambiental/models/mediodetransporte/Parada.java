package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;

public class Parada {
  private String nombre;
  private Distancia distanciaAAnteriorParada;
  private Distancia distanciaAProximaParada;

  public Parada(String nombre, Distancia distanciaAAnteriorParada, Distancia distanciaAProximaParada) {
    this.nombre = nombre;
    this.distanciaAAnteriorParada = distanciaAAnteriorParada;
    this.distanciaAProximaParada = distanciaAProximaParada;
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
