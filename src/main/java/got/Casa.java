package got;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CASAS")
public class Casa extends PersistentEntity {
  private String nombre;

  private Integer patrimonio;

  @Column(name = "anio_fundacion")
  private Integer anioFundacion;

  @ManyToOne
  @JoinColumn(name = "vasalla_de_casa_id")
  private Casa vasallaDe;

  @OneToMany
  @JoinColumn(name = "casa_id")
  private Collection<FuerzaMilitar> fuerzasMilitares;

  @ManyToOne
  private Lugar origen;

  protected Casa() {
  }

  public Casa(String nombre,
              Integer patrimonio,
              Integer anioFundacion,
              Casa vasallaDe,
              Lugar origen,
              Collection<FuerzaMilitar> fuerzasMilitares) {
    this.nombre = nombre;
    this.patrimonio = patrimonio;
    this.anioFundacion = anioFundacion;
    this.vasallaDe = vasallaDe;
    this.origen = origen;
    this.fuerzasMilitares = fuerzasMilitares;
  }

  public String nombreLugarOrigen() {
    return origen.getNombre();
  }
}
