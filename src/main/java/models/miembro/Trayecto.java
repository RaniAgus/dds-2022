package models.miembro;

import java.util.ArrayList;
import java.util.List;
import models.geolocalizacion.Distancia;

public class Trayecto {
  private List<Tramo> tramos;

  public Trayecto(List<Tramo> tramos) {
    this.tramos = new ArrayList<>(tramos);
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
