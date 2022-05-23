package models.mediodetransporte;

import models.geolocalizacion.Ubicacion;

import java.util.ArrayList;
import java.util.List;

public class Linea {
  private String nombre;
  private List<Ubicacion> paradas;
  private TipoDeTransportePublico tipo;

  public Linea(String nombre, List<Ubicacion> paradas, TipoDeTransportePublico tipo) {
    this.nombre = nombre;
    this.paradas = new ArrayList<>(paradas);
    this.tipo = tipo;
  }

  public void agregarParada(Ubicacion nuevaParada) {
    this.paradas.add(nuevaParada);
  }

  public List<Ubicacion> getParadas() {
    return paradas;
  }
}
