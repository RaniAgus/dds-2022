package ar.edu.utn.frba.dds.impactoambiental.models.mediodetransporte;

import ar.edu.utn.frba.dds.impactoambiental.models.da.TipoDeConsumo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class VehiculoParticular extends MedioDeTransporte {
  @ManyToOne
  private TipoDeVehiculoParticular tipoDeVehiculo;
  @ManyToOne
  private TipoDeConsumo tipoDeConsumo;

  protected VehiculoParticular() {
  }

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
    return tipoDeConsumo.equals(tipo);
  }
}
