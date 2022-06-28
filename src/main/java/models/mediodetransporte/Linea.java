package models.mediodetransporte;

import models.geolocalizacion.Distancia;

import java.util.ArrayList;
import java.util.List;

public class Linea {
  private String nombre;
  private List<Parada> paradas;
  private TipoDeTransportePublico tipo;

  public Linea(String nombre, List<Parada> paradas, TipoDeTransportePublico tipo) {
    this.nombre = nombre;
    this.paradas = new ArrayList<>(paradas);
    this.tipo = tipo;
  }

  public void agregarParada(Parada nuevaParada, Distancia distanciaANuevaParada) {
    if (!paradas.isEmpty()) {
      paradas.get(paradas.size() - 1).setDistanciaAProximaParada(distanciaANuevaParada);
    }
    this.paradas.add(nuevaParada);
  }

  public List<Parada> getParadas() {
    return paradas;
  }

  public Distancia distanciaEntreParadas(Parada paradaInicial, Parada paradaFinal) {
    int posInicial = paradas.indexOf(paradaInicial);
    int posFinal = paradas.indexOf(paradaFinal);

    if (posInicial < posFinal) {
      return distanciaParadasIda(posInicial, posFinal);
    }

    return distanciaParadasVuelta(posInicial, posFinal);
  }

  private Distancia distanciaParadasIda(int paradaInicial, int paradaFinal) {
    return paradas.subList(paradaInicial, paradaFinal)
        .stream()
        .map(Parada::getDistanciaAProximaParada)
        .reduce(Distancia.CERO, Distancia::sumar);
  }

  private Distancia distanciaParadasVuelta(int paradaInicial, int paradaFinal) {
    return paradas.subList(paradaFinal, paradaInicial)
        .stream()
        .map(Parada::getDistanciaAAnteriorParada)
        .reduce(Distancia.CERO, Distancia::sumar);
  }
}
