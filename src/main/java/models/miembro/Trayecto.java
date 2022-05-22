package models.miembro;

import models.api.Distancia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    Integer distanciaTotal = tramos.stream().mapToInt(tramo -> Integer.parseInt(tramo.getDistanciaDelTramo(apiDistancia).getValor())).sum();
    return new Distancia(distanciaTotal.toString(),"KM");
  }
  public Stream<Distancia> medirDistanciasParciales(Geolocalizador apiDistancia){
    return tramos.stream().map(tramo -> tramo.getDistanciaDelTramo(apiDistancia));
  }
  /*public void addViajante(Miembro unViajante){
    this.viajantes.add(unViajante);
  }*/
}
