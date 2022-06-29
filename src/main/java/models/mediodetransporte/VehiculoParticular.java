package models.mediodetransporte;

public class VehiculoParticular implements MedioDeTransporte {
  private final TipoDeVehiculoParticular tipoDeVehiculo;
  private final TipoDeCombustible tipoDeCombustible;

  public VehiculoParticular(TipoDeVehiculoParticular tipoDeVehiculo,
                            TipoDeCombustible tipoDeCombustible) {
    this.tipoDeVehiculo = tipoDeVehiculo;
    this.tipoDeCombustible = tipoDeCombustible;
  }

  @Override
  public Double carbonoEquivalentePorKM() {
    return tipoDeCombustible.factorEmision() * tipoDeVehiculo.consumoPorKM();
  }

  @Override
  public boolean esCompartible() {
    return true;
  }
}
