package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Tramo {
  @Id
  @GeneratedValue
  private long id;

  public abstract Distancia getDistancia();

  public abstract boolean esCompartible();

  public abstract Double carbonoEquivalente();

  public abstract Boolean tieneTipoDeConsumo(TipoDeConsumo tipo);
  
}
