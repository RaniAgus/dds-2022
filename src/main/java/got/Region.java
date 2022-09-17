package got;

import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Region extends PersistentEntity {
  private String nombre;

  @ManyToOne
  @JoinColumn(name = "casa_principal_id")
  private Casa casaPrincipal;

  @ManyToMany
  private Collection<Lugar> lugares;

  protected Region() {
  }

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
