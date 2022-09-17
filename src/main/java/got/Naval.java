package got;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("N")
public class Naval extends FuerzaMilitar {
  @Column(name = "cantidad_barcos")
  private Integer cantidadBarcos;

  protected Naval() {
  }

  public Naval(Integer cantidadBarcos) {
    this.cantidadBarcos = cantidadBarcos;
  }

  @Override
  public void atacarA(Lugar lugar) {

  }
}
