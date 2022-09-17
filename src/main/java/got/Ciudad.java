package got;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CIUDADES")
public class Ciudad extends Lugar {
  @Column(name = "cantidad_comercios")
  private Integer cantidadDeComercios;

  @Column(name = "cantidad_santuarios")
  private Integer cantidadDeSantuarios;

  @Column(name = "tasa_mortalidad")
  private Double tasaDeMortalidad;

  protected Ciudad() {
  }

  public Ciudad(String nombre,
                Integer anioFundacion,
                Integer poblacion,
                Integer cantidadDeComercios,
                Integer cantidadDeSantuarios,
                Double tasaDeMortalidad) {
    super(nombre, anioFundacion, poblacion);
    this.cantidadDeComercios = cantidadDeComercios;
    this.cantidadDeSantuarios = cantidadDeSantuarios;
    this.tasaDeMortalidad = tasaDeMortalidad;
  }

}
