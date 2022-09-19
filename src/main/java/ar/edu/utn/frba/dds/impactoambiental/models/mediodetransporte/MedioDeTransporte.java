package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

public interface MedioDeTransporte {
  Double carbonoEquivalentePorKM();
  boolean esCompartible();
  Boolean tieneTipoDeConsumo(TipoDeConsumo tipo);
}
