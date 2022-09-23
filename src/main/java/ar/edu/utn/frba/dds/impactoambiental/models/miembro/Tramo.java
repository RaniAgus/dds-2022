package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.EntidadPersistente;
import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Tramo extends EntidadPersistente {

  public abstract Distancia getDistancia();

  public abstract boolean esCompartible();

  public abstract Double carbonoEquivalente();

  public abstract Boolean tieneTipoDeConsumo(TipoDeConsumo tipo);

}
