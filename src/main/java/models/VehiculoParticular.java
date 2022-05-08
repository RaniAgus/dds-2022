package models;

public class VehiculoParticular implements MedioDeTransporte{
  private TipoDeVehiculoParticular tipoDeVehiculo;
  private TipoDeCombustible tipoDeCombustible;

  public VehiculoParticular(TipoDeVehiculoParticular tipoDeVehiculo,
                            TipoDeCombustible tipoDeCombustible) {
    this.tipoDeVehiculo = tipoDeVehiculo;
    this.tipoDeCombustible = tipoDeCombustible;
  }

}
