package models.miembro;

import models.geolocalizacion.Distancia;
import models.geolocalizacion.Geolocalizador;

import java.util.ArrayList;
import java.util.List;

public class Trayecto {
  private List<Tramo> tramos;
  private List<Miembro> viajantes;


  public Trayecto(List<Tramo> tramos,List<Miembro> viajantes) {
    if (!verificarSiEsCompartible(tramos)){
      throw new RuntimeException();
    }
    this.tramos = new ArrayList<>(tramos);
    this.viajantes = viajantes;
  }

  public boolean verificarSiEsCompartible(List<Tramo> tramos){
    return tramos.stream().allMatch(Tramo::esCompartible);
  }

  public List<Miembro> getViajantes() {
    return viajantes;
  }

  public Distancia medirDistanciaTrayecto() {
    return tramos.stream()
        .map(Tramo::getDistancia)
        .reduce(Distancia.CERO, Distancia::sumar);
  }

}
