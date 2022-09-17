package got;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("T")
public class Terrestre extends FuerzaMilitar {
  @Column(name = "cantidad_soldados")
  private Integer cantidadSoldados;

  protected Terrestre() {
  }

  public Terrestre(Integer cantidadSoldados) {
    this.cantidadSoldados = cantidadSoldados;
  }

  @Override
  public void atacarA(Lugar lugar) {

  }
}
