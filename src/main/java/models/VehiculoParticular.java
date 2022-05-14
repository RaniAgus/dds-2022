package models;

public class VehiculoParticular implements MedioDeTransporte {
  private final TipoDeVehiculoParticular tipoDeVehiculo;
  private final TipoDeCombustible tipoDeCombustible;

  public VehiculoParticular(TipoDeVehiculoParticular tipoDeVehiculo,
                            TipoDeCombustible tipoDeCombustible) {
    this.tipoDeVehiculo = tipoDeVehiculo;
    this.tipoDeCombustible = tipoDeCombustible;
  }

}
