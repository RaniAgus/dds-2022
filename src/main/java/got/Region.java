package got;

import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class Region {
  private String nombre;
  private Casa casaPrincipal;
  private Collection<Lugar> lugares;


  public Region(String nombre, Casa casaPrincipal, Collection<Lugar> lugares) {
    this.nombre = nombre;
    this.casaPrincipal = casaPrincipal;
    this.lugares = lugares;
  }

  public Set<Lugar> castillos() {
    return lugares.stream().filter(Castillo.class::isInstance).collect(toSet());
  }

  public Set<Lugar> ciudades() {
    return lugares.stream().filter(Ciudad.class::isInstance).collect(toSet());
  }

  public int poblacionTotal() {
    return lugares.size();
  }
}
