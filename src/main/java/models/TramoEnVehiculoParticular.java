package models;

public class TramoEnVehiculoParticular extends Tramo{
  private TipoDeVehiculoParticular tipoDeVehiculo;
  private TipoDeCombustible tipoDeCombustible;

  public TramoEnVehiculoParticular(Ubicacion ubicacionInicial,
                                   Ubicacion ubicacionFinal,
                                   TipoDeVehiculoParticular tipoDeVehiculo,
                                   TipoDeCombustible tipoDeCombustible) {
    super(ubicacionInicial, ubicacionFinal);
    this.tipoDeVehiculo = tipoDeVehiculo;
    this.tipoDeCombustible = tipoDeCombustible;
  }

}
