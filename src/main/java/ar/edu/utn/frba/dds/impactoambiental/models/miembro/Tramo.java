package ar.edu.utn.frba.dds.impactoambiental.models.miembro;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;
import ar.edu.utn.frba.dds.impactoambiental.models.geolocalizacion.Distancia;

public interface Tramo {

  Distancia getDistancia();

  boolean esCompartible();

  Double carbonoEquivalente();

  Boolean tieneTipoDeConsumo(TipoDeConsumo tipo);
  
}
