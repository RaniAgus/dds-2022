package models.miembro;

import models.geolocalizacion.Distancia;

import java.util.List;

public class Trayecto {
  private List<Tramo> tramos;

  public Trayecto(List<Tramo> tramos) {
    this.tramos = tramos;
  }

  public List<Tramo> getTramos() {
    return tramos;
  }

  public Distancia getDistancia() {
    return tramos.stream()
        .map(Tramo::getDistancia)
        .reduce(Distancia.CERO, Distancia::sumar);
  }

  public boolean esCompartible() {
    return tramos.stream().allMatch(Tramo::esCompartible);
  }

  public Double carbonoEquivalente() {
   return tramos.stream().mapToDouble(Tramo::carbonoEquivalente).sum();
  }
}
