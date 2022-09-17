package got;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Aerea extends FuerzaMilitar {
  @Column(name = "cantidad_dragones")
  private Integer cantidadDragones;

  protected Aerea() {
  }

  public Aerea(Integer cantidadDragones) {
    this.cantidadDragones = cantidadDragones;
  }

  @Override
  public void atacarA(Lugar lugar) {
  }
}
