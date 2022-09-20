package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.Entity;

@Entity
public class VehiculoParticular extends MedioDeTransporte {
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

  @Override
  public Boolean tieneTipoDeConsumo(TipoDeConsumo tipo) {
    if (tipoDeConsumo == null)
      return false;
    return tipoDeConsumo.equals(tipo);
  }
}
