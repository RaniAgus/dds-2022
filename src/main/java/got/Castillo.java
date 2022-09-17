package got;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CASTILLOS")
public class Castillo extends Lugar {
  @Column(name = "cantidad_torres")
  private Integer cantidadTorres;

  @Column(name = "cantidad_murallas")
  private Integer cantidadMurallas;

  protected Castillo() {
  }

  public Castillo(String nombre,
                  Integer anioFundacion,
                  Integer poblacion,
                  Integer cantidadTorres,
                  Integer cantidadMurallas) {
    super(nombre, anioFundacion, poblacion);
    this.cantidadTorres = cantidadTorres;
    this.cantidadMurallas = cantidadMurallas;
  }
}
