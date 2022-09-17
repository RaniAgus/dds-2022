package got;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "LUGARES")
@Inheritance(strategy = InheritanceType.JOINED)
public class Lugar extends PersistentEntity {
  private String nombre;

  @Column(name = "anio_fundacion")
  private Integer anioFundacion;

  private Integer poblacion;

  protected Lugar() {
  }

  public Lugar(String nombre,
               Integer anioFundacion,
               Integer poblacion) {
    this.nombre = nombre;
    this.anioFundacion = anioFundacion;
    this.poblacion = poblacion;
  }

  public String getNombre() {
    return nombre;
  }

}
