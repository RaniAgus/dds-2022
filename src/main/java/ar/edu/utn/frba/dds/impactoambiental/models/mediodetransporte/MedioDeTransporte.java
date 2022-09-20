package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MedioDeTransporte {
  public MedioDeTransporte() {
  }

  @Id
  @GeneratedValue
  private long id;

  public abstract Double carbonoEquivalentePorKM();
  public abstract boolean esCompartible();
  public abstract Boolean tieneTipoDeConsumo(TipoDeConsumo tipo);
}
