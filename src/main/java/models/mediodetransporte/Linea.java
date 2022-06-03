package models.mediodetransporte;

import java.util.ArrayList;
import java.util.List;
import models.geolocalizacion.Distancia;

public class Linea {
  private String nombre;
  private List<Parada> paradas;
  private TipoDeTransportePublico tipo;

  public Linea(String nombre, List<Parada> paradas, TipoDeTransportePublico tipo) {
    this.nombre = nombre;
    this.paradas = new ArrayList<>(paradas);
    this.tipo = tipo;
  }

  public void agregarParada(Parada nuevaParada) {
    this.paradas.add(nuevaParada);
  }

  public List<Parada> getParadas() {
    return paradas;
  }

  public Distancia distanciaEntreParadas(Parada paradaInicial, Parada paradaFinal) {
    return paradas.subList(paradas.indexOf(paradaInicial), paradas.indexOf(paradaFinal))
        .stream()
        .map(Parada::getDistanciaAProximaParada)
        .reduce(Distancia.CERO, Distancia::sumar);
  }
}
