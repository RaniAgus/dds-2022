package mia;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;

public class Linea {
  private String nombre;
  private List<Ubicacion> paradas;
  private TipoDeTransportePublico tipo;

  public Linea(String nombre, List<Ubicacion> paradas, TipoDeTransportePublico tipo) {
    this.nombre = nombre;
    this.paradas = new ArrayList<>(paradas);
    this.tipo = requireNonNull(tipo,"No se puede crear un tramo en transporte publico sin tipo");
  }

  public void agregarParada(Ubicacion nuevaParada) {
    this.paradas.add(nuevaParada);
  }

  public String getNombre() {
    return nombre;
  }

  public List<Ubicacion> getParadas() {
    return new ArrayList<>(paradas);
  }
}
