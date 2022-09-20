package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MedioDeTransporte extends EntidadPersistente {
  public abstract Double carbonoEquivalentePorKM();
  public abstract boolean esCompartible();
  public abstract Boolean tieneTipoDeConsumo(TipoDeConsumo tipo);
}
