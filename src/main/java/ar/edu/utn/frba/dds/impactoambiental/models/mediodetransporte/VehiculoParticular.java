package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

public class VehiculoParticular implements MedioDeTransporte {
  private final TipoDeVehiculoParticular tipoDeVehiculo;
  private final TipoDeConsumo tipoDeConsumo;

  public VehiculoParticular(TipoDeVehiculoParticular tipoDeVehiculo, TipoDeConsumo tipoDeConsumo) {
    this.tipoDeVehiculo = tipoDeVehiculo;
    this.tipoDeConsumo = tipoDeConsumo;
  }

  @Override
  public Double carbonoEquivalentePorKM() {
    return tipoDeConsumo.getFactorEmision() * tipoDeVehiculo.consumoPorKM();
  }

  @Override
  public boolean esCompartible() {
    return true;
  }
}
