package models.miembro;

import java.util.ArrayList;
import java.util.List;
import models.geolocalizacion.Distancia;

public class Trayecto {
  private List<Tramo> tramos;
  private List<Miembro> viajantes;

  public Trayecto(List<Tramo> tramos, List<Miembro> viajantes) {
    this.tramos = new ArrayList<>(tramos);
    this.viajantes = viajantes;
  }

  public List<Miembro> getViajantes() {
    return viajantes;
  }

  public Distancia medirDistanciaTrayecto() {
    return tramos.stream()
        .map(Tramo::getDistancia)
        .reduce(Distancia.CERO, Distancia::sumar);
  }

  public Double carbonoEquivalente(){
   return tramos.stream().mapToDouble(Tramo::carbonoEquivalente).sum();
  }
}
