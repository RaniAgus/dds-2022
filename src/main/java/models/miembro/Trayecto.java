package models.miembro;

import models.geolocalizacion.Distancia;
import models.geolocalizacion.Geolocalizador;

import java.util.ArrayList;
import java.util.List;

public class Trayecto {
  private List<Tramo> tramos;
  private List<Miembro> viajantes;


  public Trayecto(List<Tramo> tramos,List<Miembro> viajantes) {
    this.tramos = new ArrayList<>(tramos);
    this.viajantes = viajantes;
  }

  public List<Miembro> getViajantes() {
    return viajantes;
  }

  public Distancia medirDistanciaTrayecto(Geolocalizador apiDistancia){
    return tramos.stream()
        .map(tramo -> tramo.getDistanciaDelTramo(apiDistancia))
        .reduce(Distancia.CERO, Distancia::sumar);
  }

}
