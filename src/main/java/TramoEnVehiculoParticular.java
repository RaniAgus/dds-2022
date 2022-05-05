import static java.util.Objects.requireNonNull;

public class TramoEnVehiculoParticular extends Tramo{
  private TipoDeVehiculoParticular tipoDeVehiculo;
  private TipoDeCombustible tipoDeCombustible;

  public TramoEnVehiculoParticular(Ubicacion ubicacionInicial,
                                   Ubicacion ubicacionFinal,
                                   TipoDeVehiculoParticular tipoDeVehiculo,
                                   TipoDeCombustible tipoDeCombustible) {
    super(ubicacionInicial, ubicacionFinal);
    this.tipoDeVehiculo = requireNonNull(tipoDeVehiculo,"No se puede crear un tramo en vehiculo particular sin especificar el tipo");
    this.tipoDeCombustible = requireNonNull(tipoDeCombustible,"No se puede crear un tramo en vehiculo particular sin especificar su combustible");
  }

}
